package com.example.kafka.consumer;

import com.example.kafka.producer.UserProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class UserConsumer {

    private static final Logger logger = LoggerFactory.getLogger(UserConsumer.class);

    @KafkaListener(topics = "user-events", groupId = "user-group")
    public void consumeUserEvent(UserProducer.UserEvent event) {
        logger.info("Received user event: {} - User: {} - Timestamp: {}",
                event.getOperation(),
                event.getUser().getName(),
                event.getTimestamp());

        // 여기서 추가적인 비즈니스 로직을 처리할 수 있습니다
        // 예: 로깅, 알림, 분석 등
        processUserEvent(event);
    }

    private void processUserEvent(UserProducer.UserEvent event) {
        switch (event.getOperation()) {
            case "CREATE":
                logger.info("Processing user creation: {}", event.getUser().getEmail());
                break;
            case "UPDATE":
                logger.info("Processing user update: {}", event.getUser().getEmail());
                break;
            case "DELETE":
                logger.info("Processing user deletion: {}", event.getUser().getEmail());
                break;
            default:
                logger.warn("Unknown operation: {}", event.getOperation());
        }
    }
}
