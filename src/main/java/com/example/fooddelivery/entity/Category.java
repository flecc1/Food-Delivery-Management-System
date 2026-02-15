package com.example.fooddelivery.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Category {
    private Long id;
    private String name;
    private List<Dish> dishes;
}
