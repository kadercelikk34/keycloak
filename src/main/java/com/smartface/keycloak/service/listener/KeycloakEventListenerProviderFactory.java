package com.smartface.keycloak.service.listener;

import com.smartface.keycloak.service.kafka.KafkaProducerService;
import lombok.AllArgsConstructor;
import org.keycloak.Config;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventListenerProviderFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class KeycloakEventListenerProviderFactory implements EventListenerProviderFactory {

    private final KafkaProducerService eventProducer;

    @Override
    public EventListenerProvider create(KeycloakSession keycloakSession) {
        return new KeycloakEventListenerProvider(eventProducer);
    }

    @Override
    public void init(Config.Scope scope) {

    }

    @Override
    public void postInit(KeycloakSessionFactory keycloakSessionFactory) {

    }

    @Override
    public void close() {

    }

    @Override
    public String getId() {
        return "keycloak-event-listener";
    }
}
