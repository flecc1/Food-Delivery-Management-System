package com.example.fooddelivery.mapper;

import com.example.fooddelivery.dto.menu.MenuCreateDto;
import com.example.fooddelivery.dto.menu.MenuDto;
import com.example.fooddelivery.entity.Menu;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class MenuMapper {
    private final DishMapper dishMapper;

    public MenuDto toDto(Menu menu) {
        if (menu == null) {
            return null;
        }
        MenuDto menuDto = new MenuDto();
        menuDto.setId(menu.getId());
        menuDto.setName(menu.getName());
        menuDto.setDescription(menu.getDescription());
        menuDto.setActive(menu.isActive());

        if (menu.getRestaurant() != null) {
            menuDto.setRestaurantName(menu.getRestaurant().getName());
        }
        if (menu.getDishes() != null) {
            menuDto.setDishes(menu.getDishes()
                    .stream()
                    .map(dishMapper::toDto)
                    .toList());
        } else {
            menuDto.setDishes(Collections.emptyList());
        }
        return menuDto;
    }

    public Menu toEntity(MenuCreateDto menuCreateDto) {
        if (menuCreateDto == null) {
            return null;
        }
        Menu menu = new Menu();
        menu.setName(menuCreateDto.getName());
        menu.setDescription(menuCreateDto.getDescription());
        return menu;
    }
}
