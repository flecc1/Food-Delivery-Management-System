package com.example.fooddelivery.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Menu {
    private Long id;
    private String name;
    private String description;
    private boolean active;
    private Restaurant restaurant;
    private List<Dish> dishes;
}
