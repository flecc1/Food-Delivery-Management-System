package com.example.fooddelivery.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
public class MenuDto {
    private Long id;
    private String name;
    private String description;
    private List<DishDto> dishes;
}
