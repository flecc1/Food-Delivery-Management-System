package com.example.fooddelivery.dto;

import com.example.fooddelivery.entity.Dish;
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
}
