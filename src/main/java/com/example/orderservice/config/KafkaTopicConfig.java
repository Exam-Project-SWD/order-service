package com.example.orderservice.config;

import com.example.orderservice.model.enums.Topic;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
    @Bean
    public NewTopic sendOrderToRestaurant() {
        return TopicBuilder.name(Topic.NEW_ORDER.toString()).build();
    }
}