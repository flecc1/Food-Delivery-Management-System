package com.example.fooddelivery.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Restaurant {
    private Long id;
    private String name;
    private String address;
    private String city;
    private String rating;
    private List<Dish> menu;
    private  List<Order> orders;
}
