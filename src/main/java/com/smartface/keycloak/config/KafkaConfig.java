package com.smartface.keycloak.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        // Set the maximum number of retries for message delivery
        // Configured to retry indefinitely to avoid message loss
        configProps.put(ProducerConfig.RETRIES_CONFIG, Integer.toString(Integer.MAX_VALUE));

        // Enable idempotence to prevent duplicate messages in case of retries
        configProps.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true");

        // Set acknowledgment level to 'all' to ensure data is replicated and committed
        configProps.put(ProducerConfig.ACKS_CONFIG, "all");

        // Set the maximum time (in ms) to wait for message delivery before timing out
        configProps.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, 120000);  // 2 minutes timeout

        // Set the delay (in ms) between retry attempts in case of failure
        configProps.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, 1000);  // 1 second delay between retries

        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    private ConsumerFactory<String, String> consumerFactory() {
        Map<String, Object> consumerProps = new HashMap<>();
        consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(consumerProps);
    }


}
