package com.example.orderservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CartDTO {
    private int customerId;
    private int restaurantId;
    private double totalPrice;
    private boolean withDelivery = true;
    private List<CartItemDTO> items = new ArrayList<>();
}