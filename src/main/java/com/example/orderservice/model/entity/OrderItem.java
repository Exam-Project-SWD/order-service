package com.example.orderservice.model.entity;

import com.example.orderservice.model.dto.OrderItemDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Entity
@Table(name = "order_item")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "menuItemId")
    private int menuItemId;
    @Column(name = "quantity")
    private int quantity;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    public OrderItem(int menuItemId, int quantity) {
        this.menuItemId = menuItemId;
        this.quantity = quantity;
    }

    public static List<OrderItem> fromList(List<OrderItemDTO> items) {
        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemDTO item : items
        ) {
            OrderItem orderItem = new OrderItem(item.getMenuItemId(), item.getQuantity());
            orderItems.add(orderItem);
        }
        return orderItems;
    }
}