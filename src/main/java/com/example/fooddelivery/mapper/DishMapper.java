package com.example.fooddelivery.mapper;

import com.example.fooddelivery.dto.dish.DishCreateDto;
import com.example.fooddelivery.dto.dish.DishDto;
import com.example.fooddelivery.entity.Dish;
import org.springframework.stereotype.Component;

@Component
public class DishMapper {
    public DishDto toDto(Dish dish) {
        if (dish == null) {
            return null;
        }
        DishDto dishDto = new DishDto();
        dishDto.setId(dish.getId());
        dishDto.setName(dish.getName());
        dishDto.setPrice(dish.getPrice());
        dishDto.setDescription(dish.getDescription());
        if (dish.getCategory() != null) {
            dishDto.setCategoryName(dish.getCategory().getName());
        }

        return dishDto;
    }

    public Dish toEntity(DishCreateDto dishCreateDto) {
        if (dishCreateDto == null) {
            return null;
        }
        Dish dish = new Dish();
        dish.setName(dishCreateDto.getName());
        dish.setPrice(dishCreateDto.getPrice());
        dish.setDescription(dishCreateDto.getDescription());
        return dish;
    }
}
