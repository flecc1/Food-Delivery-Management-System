package com.example.fooddelivery.dto.order;

import lombok.Data;

import java.util.List;

@Data
public class OrderCreateDto {
    private List<Long> dishes_id;
    private String address;
}
