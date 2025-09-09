package com.example.kafka.producer;

import com.example.kafka.dto.BoardRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class BoardProducer {
    @Value("${spring.kafka.topic}")
    private String TOPIC;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void sendBoardEvent(String operation, BoardRequest req) {
        CompletableFuture<SendResult<String, Object>> future =
            kafkaTemplate.send(TOPIC, operation, req);

        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Event Success: {}", req.getTitle());
            } else {
                log.error("Failed: {}", req.getTitle(), ex);
            }
        });
    }
}
