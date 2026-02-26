package com.example.fooddelivery.dto;

import com.example.fooddelivery.entity.Category;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DishDto {
    private Long id;
    private String name;
    private double price;
    private String description;
    private Category category;
}
