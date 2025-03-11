package com.smartface.keycloak.service.kafka;

import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@EnableKafka
public class KafkaConsumerService {

    @KafkaListener(topics = "keycloak-events", groupId = "keycloak-events-group")
    public void listen(String event) {
        // Process the Keycloak event
        System.out.println("Received Keycloak Event: " + event);
    }
}
