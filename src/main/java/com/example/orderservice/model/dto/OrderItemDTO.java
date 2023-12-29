package com.example.orderservice.model.dto;

import com.example.orderservice.model.entity.OrderItem;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderItemDTO {
    private int id;
    private int menuItemId;
    private int quantity;
    private int orderId;

    public static List<OrderItemDTO> fromList(List<OrderItem> items) {
        List<OrderItemDTO> orderItems = new ArrayList<>();
        for (OrderItem item : items
        ) {
            orderItems.add(new OrderItemDTO(item.getId(),
                    item.getMenuItemId(),
                    item.getQuantity(), item.getOrder().getId()));
        }
        return orderItems;
    }

    public static List<OrderItemDTO> fromNewItems(List<NewOrderItem> items) {
        List<OrderItemDTO> orderItems = new ArrayList<>();
        for (NewOrderItem item : items
        ) {
            OrderItemDTO dto = new OrderItemDTO();
            dto.setMenuItemId(item.getMenuItemId());
            dto.setQuantity(item.getQuantity());
            orderItems.add(dto);
        }
        return orderItems;
    }
}