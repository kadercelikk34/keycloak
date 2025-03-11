package com.smartface.keycloak.service.kafka;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class KafkaProducerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducerService.class);

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendEventToKafka(String topic, String message) {
        try {
            kafkaTemplate.send(topic, message);
        } catch (KafkaException e) {
            LOGGER.error("Failed to send message to Kafka: {}", e.getMessage());
        }
    }
}
