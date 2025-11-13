package com.example.kafka.producer;

import com.example.kafka.dto.BoardRequest;
import com.example.kafka.enums.KafkaType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BoardProducer {
    @Value("${spring.kafka.topic1}")
    private String TOPIC1;

    @Value("${spring.kafka.topic2}")
    private String TOPIC2;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void sendBoardEvent(KafkaType type, BoardRequest req) {
        if (type == KafkaType.CREATE || type == KafkaType.COUNT) {
            kafkaTemplate.send(type == KafkaType.CREATE ? TOPIC1 : TOPIC2, type.name(), req);
        }
    }
}
