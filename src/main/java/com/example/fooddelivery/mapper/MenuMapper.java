package com.example.fooddelivery.mapper;

import com.example.fooddelivery.dto.MenuDto;
import com.example.fooddelivery.entity.Menu;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MenuMapper {
    private final DishMapper dishMapper;
    public MenuDto toDto(Menu menu) {
        if(menu == null) {
            return null;
        }
        MenuDto menuDto = new MenuDto();
        menuDto.setId(menu.getId());
        menuDto.setName(menu.getName());
        menuDto.setDescription(menu.getDescription());
        menuDto.setDishes(menu.getDishes()
                .stream()
                .map(dishMapper::toDto)
                .toList());
        return menuDto;
    }
}
