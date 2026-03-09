package com.example.fooddelivery.dto.order;

import lombok.Data;

import java.util.List;

@Data
public class OrderCreateDto {
    private List<Long> dishesId;
    private String address;
    private Long customerId;
    private Long restaurantId;
}