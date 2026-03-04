package com.example.fooddelivery.controller;

import com.example.fooddelivery.dto.menu.MenuCreateDto;
import com.example.fooddelivery.dto.menu.MenuDto;

import com.example.fooddelivery.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/menus")
@RequiredArgsConstructor
public class MenuController {
    private final MenuService menuService;

    @GetMapping("/{id:\\d+}")
    public MenuDto getMenuById(@PathVariable Long id) {
        return menuService.findById(id);
    }

    @GetMapping("/restaurant/{restaurantId:\\d+}")
    public List<MenuDto> getMenuByRestaurantId(@PathVariable Long restaurantId) {
        return menuService.findMenuByRestaurantId(restaurantId);
    }

    @GetMapping
    public List<MenuDto> getAllMenus(@RequestParam(value = "name", required = false) String name) {
        if (name != null) {
            return menuService.findMenuByName(name);
        }
        return menuService.findAllMenus();
    }

    @PostMapping
    public MenuDto addMenu(@RequestBody MenuCreateDto menuCreateDto) {
        return menuService.addMenu(menuCreateDto);
    }

    @PostMapping("/{menuId}/dishes/{dishId}")
    public MenuDto addExistingDishToMenu(@PathVariable Long menuId, @PathVariable Long dishId) {
        return menuService.addDishToMenu(menuId, dishId);
    }

    @PutMapping("/{id:\\d+}")
    public MenuDto updateMenuById(@PathVariable Long id, @RequestBody MenuCreateDto menuCreateDto) {
        return menuService.updateMenuById(id, menuCreateDto);
    }

    @DeleteMapping("/{id:\\d+}")
    public void deleteMenuById(@PathVariable Long id) {
        menuService.deleteMenuById(id);
    }
}
