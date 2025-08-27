package com.example.kafka.producer;

import com.example.kafka.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class UserProducer {

    private static final Logger logger = LoggerFactory.getLogger(UserProducer.class);
    private static final String TOPIC = "user-events";

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void sendUserEvent(String operation, User user) {
        UserEvent event = new UserEvent(operation, user);

        CompletableFuture<SendResult<String, Object>> future =
                kafkaTemplate.send(TOPIC, user.getId().toString(), event);

        future.whenComplete((result, ex) -> {
            if (ex == null) {
                logger.info("User event sent successfully: {} - User ID: {}",
                        operation, user.getId());
            } else {
                logger.error("Failed to send user event: {} - User ID: {}",
                        operation, user.getId(), ex);
            }
        });
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class UserEvent {
        private String operation;
        private User user;
        private long timestamp;

        UserEvent(String operation, User user) {
            this.operation = operation;
            this.user = user;
        }
    }
}
