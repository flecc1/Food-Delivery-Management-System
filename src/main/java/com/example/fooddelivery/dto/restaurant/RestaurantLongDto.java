package com.example.fooddelivery.dto.restaurant;

import com.example.fooddelivery.dto.DishDto;
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
    private List<DishDto> dishes;
}
