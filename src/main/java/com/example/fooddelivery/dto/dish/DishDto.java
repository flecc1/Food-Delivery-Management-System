package com.example.fooddelivery.dto.dish;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DishDto {
    private Long id;
    private String name;
    private double price;
    private String description;
    private String categoryName;
    private Long restaurantId;
    private String restaurantName;
}
