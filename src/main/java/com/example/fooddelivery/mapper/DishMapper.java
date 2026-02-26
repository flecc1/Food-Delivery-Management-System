package com.example.fooddelivery.mapper;

import com.example.fooddelivery.dto.DishDto;
import com.example.fooddelivery.entity.Dish;
import org.springframework.stereotype.Component;

@Component
public class DishMapper {
    public DishDto toDto(Dish dish){
        if(dish == null) {
            return null;
        }
        DishDto dishDto = new DishDto();
        dishDto.setId(dish.getId());
        dishDto.setName(dish.getName());
        dishDto.setPrice(dish.getPrice());
        dishDto.setDescription(dish.getDescription());
        dishDto.setCategory(dish.getCategory());
        return dishDto;
    }
}
