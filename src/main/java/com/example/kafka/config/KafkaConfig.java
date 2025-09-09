package com.example.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.topic}")
    private String topic;

    @Bean
    public NewTopic boardTopic() {
        return TopicBuilder.name(topic)
            .partitions(3)
            .replicas(1)
            .build();
    }

    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

//    @Bean
//    public KStream<String, String> kStream(StreamsBuilder builder) {
//        KStream<String, String> stream = builder.stream("input-topic",
//            Consumed.with(Serdes.String(), Serdes.String()));
//
//        stream
//            .groupByKey()
//            .windowedBy(TimeWindows.ofSizeWithNoGrace(Duration.ofSeconds(5)))
//            .aggregate(
//                () -> "",
//                (key, value, aggregate) -> aggregate.isEmpty() ? value : aggregate + "," + value,
//                Materialized.with(Serdes.String(), Serdes.String())
//            )
//            .toStream()
//            .map((windowedKey, aggregatedValue) -> {
//                String newKey = windowedKey.key();
//                String newValue = String.format("Window[%d-%d]: %s",
//                    windowedKey.window().start(),
//                    windowedKey.window().end(),
//                    aggregatedValue);
//                return KeyValue.pair(newKey, newValue);
//            })
//            .to("output-topic", Produced.with(Serdes.String(), Serdes.String()));
//
//        return stream;
//    }
}
