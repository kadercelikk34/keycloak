package com.smartface.keycloak.service.listener;

import com.smartface.keycloak.service.kafka.KafkaProducerService;
import com.smartface.keycloak.topic.Topic;
import lombok.AllArgsConstructor;
import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.admin.AdminEvent;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class KeycloakEventListenerProvider implements EventListenerProvider {
    private final KafkaProducerService kafkaProducerService;

    @Override
    public void onEvent(Event event) {
        switch (event.getType()) {
            case REGISTER:
                kafkaProducerService.sendEventToKafka(Topic.REGISTER, event.getUserId());
                break;
            case CLIENT_LOGIN:
                kafkaProducerService.sendEventToKafka(Topic.CLIENT_LOGIN, event.getUserId());
                break;
            case UPDATE_PASSWORD:
                kafkaProducerService.sendEventToKafka(Topic.UPDATE_PASSWORD, event.getUserId());
                break;
            case DELETE_ACCOUNT:
                kafkaProducerService.sendEventToKafka(Topic.DELETE_ACCOUNT, event.getUserId());
                break;
            case VERIFY_EMAIL:
                kafkaProducerService.sendEventToKafka(Topic.VERIFY_EMAIL, event.getUserId());
                break;
        }
    }

    @Override
    public void onEvent(AdminEvent adminEvent, boolean b) {

    }

    @Override
    public void close() { }
}

