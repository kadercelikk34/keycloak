package com.smartface.keycloak.service.listener;

import com.smartface.keycloak.service.kafka.KafkaProducerService;
import com.smartface.keycloak.topic.Topic;
import lombok.AllArgsConstructor;
import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventType;
import org.keycloak.events.admin.AdminEvent;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class KeycloakEventListenerProvider implements EventListenerProvider {
    private final KafkaProducerService kafkaProducerService;

    @Override
    public void onEvent(Event event) {
        if (EventType.REGISTER.equals(event.getType())) {
            kafkaProducerService.sendEventToKafka(Topic.REGISTER, event.getUserId());
        }

        if (EventType.CLIENT_LOGIN.equals(event.getType())) {
            kafkaProducerService.sendEventToKafka(Topic.CLIENT_LOGIN, event.getUserId());
        }

        if (EventType.UPDATE_PASSWORD.equals(event.getType())) {
            kafkaProducerService.sendEventToKafka(Topic.UPDATE_PASSWORD, event.getUserId());
        }

        if (EventType.DELETE_ACCOUNT.equals(event.getType())) {
            kafkaProducerService.sendEventToKafka(Topic.DELETE_ACCOUNT, event.getUserId());
        }

        if (EventType.VERIFY_EMAIL.equals(event.getType())) {
            kafkaProducerService.sendEventToKafka(Topic.VERIFY_EMAIL, event.getUserId());
        }

    }

    @Override
    public void onEvent(AdminEvent adminEvent, boolean b) {

    }

    @Override
    public void close() {

    }
}
