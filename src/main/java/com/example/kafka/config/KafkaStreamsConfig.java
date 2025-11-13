package com.example.kafka.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class KafkaStreamsConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${spring.kafka.topic2}")
    private String TOPIC2;

    @Value("${spring.kafka.topic3}")
    private String TOPIC3;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Bean
    public KafkaStreams kafkaStreams() {
        final StreamsBuilder streamsBuilder = new StreamsBuilder();

        streamsBuilder.stream(TOPIC2).map((key,value) -> {
            try {
                return KeyValue.pair("dto", value);
            } catch (Exception e) {
                return KeyValue.pair("dto", null);
            }
        })
        .to(TOPIC3);

        final Properties prop = new Properties();
        prop.put(StreamsConfig.APPLICATION_ID_CONFIG, applicationName + "-streams");
        prop.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        prop.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        prop.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        prop.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 5000);

        final KafkaStreams kafkaStreams = new KafkaStreams(streamsBuilder.build(), prop);
        kafkaStreams.start();

        return kafkaStreams;
    }
}