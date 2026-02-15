package com.example.fooddelivery.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Dish {
    private Long id;
    private String name;
    private double price;
    private String description;
}
