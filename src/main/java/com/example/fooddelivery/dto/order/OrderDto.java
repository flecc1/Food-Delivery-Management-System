package com.example.fooddelivery.dto.order;

import com.example.fooddelivery.dto.dish.DishDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class OrderDto {
    private Long id;
    private String status;
    private LocalDateTime createdAt;
    private int amount;
    private List<DishDto> dishes;
    //TODO DOUBLE на BIGDECIMAL
    private double price;
    private String address;
    private Long customerId;
}
