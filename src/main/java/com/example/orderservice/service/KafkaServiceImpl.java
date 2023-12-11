package com.example.orderservice.service;

import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaServiceImpl implements KafkaService {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaServiceImpl.class);
    private NewTopic topic;
    private KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaServiceImpl(NewTopic topic, KafkaTemplate<String, Object> kafkaTemplate) {
        this.topic = topic;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(Object obj) {
        LOGGER.info(String.format("Order"));
    }

    @Override
    public void newCustomerEvent() {

    }
}
