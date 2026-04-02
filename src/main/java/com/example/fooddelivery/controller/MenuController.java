package com.example.fooddelivery.controller;

import com.example.fooddelivery.dto.dish.DishCreateDto;
import com.example.fooddelivery.dto.menu.MenuCreateDto;
import com.example.fooddelivery.dto.menu.MenuDto;
import com.example.fooddelivery.service.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

@Slf4j
@RestController
@RequestMapping("/api/v1/menus")
@RequiredArgsConstructor
@Tag(name = "Меню", description = "Управление меню ресторанов")
public class MenuController {
    private final MenuService menuService;

    @GetMapping("/{id:\\d+}")
    @Operation(summary = "Найти меню по ID")
    public MenuDto getMenuById(@PathVariable Long id) {
        log.info("request to getMenuById with id: {}", id);
        return menuService.findById(id);
    }

    @GetMapping("/restaurant/{restaurantId:\\d+}")
    @Operation(summary = "Получить все меню конкретного ресторана")
    public Page<MenuDto> getMenusByRestaurantId(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @PathVariable Long restaurantId) {
        log.info("request to getMenusByRestaurantId with id: {}, page: {}, size: {}", restaurantId, page, size);
        Pageable pageable = PageRequest.of(page, size);
        return menuService.findMenuByRestaurantId(restaurantId, pageable);
    }

    @GetMapping
    @Operation(summary = "Список всех меню", description = "Поддерживает фильтрацию по названию")
    public Page<MenuDto> getAllMenus(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(value = "name", required = false) String name) {
        log.info("request to getAllMenus with name: {}, page: {}, size: {}", name, page, size);
        Pageable pageable = PageRequest.of(page, size);
        if (name != null) {
            return menuService.findMenuByName(name, pageable);
        }
        return menuService.findAllMenus(pageable);
    }

    @PostMapping
    @Operation(summary = "Создать новое меню")
    public MenuDto createMenu(@Valid @RequestBody MenuCreateDto menuCreateDto) {
        log.info("request to add menu: with name: {}", menuCreateDto.getName());
        return menuService.addMenu(menuCreateDto);
    }

    @PostMapping("/{menuId}/dishes/bulk")
    @Operation(summary = "Добавить несколько блюд в сущестующее меню")
    public MenuDto addDishesToMenu(@PathVariable Long menuId, @RequestBody @Valid List<DishCreateDto> dishesList) {
        log.info("request to add some dishes to menu with id: {}", menuId);
        return menuService.addDishesToMenu(menuId, dishesList);
    }

    @PostMapping("/{menuId}/dishes/{dishId}")
    @Operation(summary = "Добавить существующее блюдо в меню")
    public MenuDto addExistingDishToMenu(@PathVariable Long menuId, @PathVariable Long dishId) {
        log.info("request to add existing dish to menu with menu id: {}, and dish id {}", menuId, dishId);
        return menuService.addDishToMenu(menuId, dishId);
    }

    @PutMapping("/{id:\\d+}")
    @Operation(summary = "Обновить данные меню")
    public MenuDto updateMenuById(@PathVariable Long id, @Valid @RequestBody MenuCreateDto menuCreateDto) {
        log.info("request to update menu with id: {}, with name: {}", id, menuCreateDto.getName());
        return menuService.updateMenuById(id, menuCreateDto);
    }

    @DeleteMapping("/{id:\\d+}")
    @Operation(summary = "Удалить меню")
    public void deleteMenuById(@PathVariable Long id) {
        log.info("request to delete menu with id: {}", id);
        menuService.deleteMenuById(id);
    }
}
