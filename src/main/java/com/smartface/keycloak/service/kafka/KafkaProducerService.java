package com.smartface.keycloak.service.kafka;

import lombok.AllArgsConstructor;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;


    public void sendEventToKafka(String topic, String message) {
        try {
            kafkaTemplate.send(topic, message);
        } catch (KafkaException e) {
            System.out.println("Failed to send message to Kafka: " + e.getMessage());
        }
    }
}
