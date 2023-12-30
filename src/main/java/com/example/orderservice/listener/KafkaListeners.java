package com.example.orderservice.listener;

import com.example.orderservice.model.dto.CartDTO;
import com.example.orderservice.model.dto.OrderDTO;
import com.example.orderservice.model.entity.Order;
import com.example.orderservice.service.KafkaService;
import com.example.orderservice.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaListeners {
    @Autowired
    private final OrderService orderService;
    @Autowired
    private final KafkaService kafkaService;

    @KafkaListener(topics = "NEW_ORDER_PLACED", groupId = "new-order-placed-id")
    public void getNewOrderPlaced(String message) {
        System.out.println(message);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            CartDTO cart = objectMapper.readValue(message, CartDTO.class);

            OrderDTO order = new OrderDTO(cart);

            orderService.saveOrder(order);

            kafkaService.sendOrderToRestaurant(order);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @KafkaListener(topics = "ORDER_ACCEPTED", groupId = "order-accepted-id")
    public void getOrderAccepted(String message) {
        System.out.println(message);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            OrderDTO order = objectMapper.readValue(message, OrderDTO.class);

            //Order newOrder = Order.fromDto(orderService.getOrderById(order.getCustomerId()));

            orderService.saveOrder(order);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}