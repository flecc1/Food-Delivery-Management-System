package com.example.fooddelivery.controller;

import com.example.fooddelivery.dto.menu.MenuCreateDto;
import com.example.fooddelivery.dto.menu.MenuDto;
import com.example.fooddelivery.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    @PutMapping("/{id:\\d+}")
    public MenuDto updateMenuById(@PathVariable Long id, @RequestBody MenuCreateDto menuCreateDto) {
        return menuService.updateMenuById(id, menuCreateDto);
    }
    @DeleteMapping("/{id:\\d+}")
    public void deleteMenuById(@PathVariable Long id) {
        menuService.deleteMenuById(id);
    }
}
