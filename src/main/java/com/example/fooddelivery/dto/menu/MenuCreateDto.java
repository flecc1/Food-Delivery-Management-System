package com.example.fooddelivery.dto.menu;

import com.example.fooddelivery.dto.dish.DishDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class MenuCreateDto {
    private String name;
    private String description;
    private List<Long> dishesIds;
    private Long restaurantId;
}
