package com.example.orderservice.config;

import com.example.orderservice.model.dto.CartDTO;
import com.example.orderservice.model.dto.OrderDTO;
import com.example.orderservice.model.dto.OrderItemDTO;
import com.example.orderservice.model.entity.Order;
import com.example.orderservice.model.entity.OrderItem;
import com.example.orderservice.model.enums.OrderStatus;
import com.example.orderservice.repository.OrderRepository;
import com.example.orderservice.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class KafkaConsumerConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Autowired
    private final OrderService orderService;

    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        HashMap<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class); // Use StringDeserializer for the value type

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new StringDeserializer());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> menuListener() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    @KafkaListener(topics = "NEW_ORDER_PLACED", groupId = "new-order-id")
    public void listen(String message) {
        System.out.println(message);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            CartDTO cart = objectMapper.readValue(message, CartDTO.class);

            OrderDTO order = new OrderDTO(cart);
//
//                    Order.builder()
//                    .customerId(cart.getCustomerId())
//                    .restaurantId(cart.getRestaurantId())
//                    .createdAt(new Timestamp(System.currentTimeMillis()))
//                    .status(OrderStatus.PENDING)
//                    .items(OrderItem.fromList(OrderItemDTO.fromCartItems(cart.getItems())))
//                    .orderPrice(cart.getTotalPrice())
//                    .deliveryPrice(29)
//                    .withDelivery(cart.isWithDelivery())
//                    .build();

            orderService.saveOrder(order);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
