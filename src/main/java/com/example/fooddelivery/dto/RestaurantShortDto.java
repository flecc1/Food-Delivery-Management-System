package com.example.fooddelivery.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RestaurantShortDto {
    private Long id;
    private String name;
    private double rating;
}
