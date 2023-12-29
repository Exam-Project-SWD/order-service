package com.example.orderservice.service;

import com.example.orderservice.model.dto.OrderDTO;
import com.example.orderservice.model.enums.Topic;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class KafkaService {
    @Autowired
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public String sendOrderToRestaurant(OrderDTO order) {
        kafkaTemplate.send(Topic.NEW_ORDER.name(), order);
        return "NEW_ORDER was published to kafka";
    }
}
