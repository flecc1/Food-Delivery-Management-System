package com.example.fooddelivery.dto.order;

import com.example.fooddelivery.dto.dish.DishDto;
import com.example.fooddelivery.status.OrderStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class OrderDto {
    private Long id;
    private OrderStatus status;
    private LocalDateTime createdAt;
    private int amount;
    private List<DishDto> dishes;
    private double totalPrice;
    private String address;
    private Long customerId;
    private String customerName;
}