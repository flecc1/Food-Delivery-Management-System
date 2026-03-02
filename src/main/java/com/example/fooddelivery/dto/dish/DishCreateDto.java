package com.example.fooddelivery.dto.dish;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DishCreateDto {
    private String name;
    private double price;
    private String description;
    private Long categoryId;
}
