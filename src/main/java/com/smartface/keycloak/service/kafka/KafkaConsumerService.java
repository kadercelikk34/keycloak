package com.smartface.keycloak.service.kafka;

import com.smartface.keycloak.topic.Topic;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@EnableKafka
public class KafkaConsumerService {

    @KafkaListener(topics = Topic.REGISTER, groupId = "user")
    public void consumeRegister(String event) {
        System.out.println("Received register Event: " + event);
    }

    @KafkaListener(topics = Topic.CLIENT_LOGIN, groupId = "user")
    public void consumeLogin(String event) {
        System.out.println("Received Keycloak Event: " + event);
    }

    @KafkaListener(topics = Topic.UPDATE_PASSWORD, groupId = "user")
    public void consumeUpdatePassword(String event) {
        System.out.println("Received update password Event: " + event);
    }

    @KafkaListener(topics = Topic.UPDATE_PASSWORD, groupId = "user")
    public void consumeDeleteAccount(String event) {
        System.out.println("Received delete account Event: " + event);
    }

    @KafkaListener(topics = Topic.VERIFY_EMAIL, groupId = "user")
    public void consumeVerifyEmail(String event) {
        System.out.println("Received verify email Event: " + event);
    }
}
