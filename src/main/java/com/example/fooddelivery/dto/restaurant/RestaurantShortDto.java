package com.example.fooddelivery.dto.restaurant;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RestaurantShortDto {
    private Long id;
    private String name;
    private double rating;
}
