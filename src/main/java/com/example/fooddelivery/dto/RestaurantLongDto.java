package com.example.fooddelivery.dto;

import com.example.fooddelivery.entity.Dish;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class RestaurantLongDto {
    private long id;
    private String name;
    private String address;
    private double rating;
    private List<Dish> dishes;
}
