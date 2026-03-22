package com.example.fooddelivery.controller;

import com.example.fooddelivery.dto.dish.DishCreateDto;
import com.example.fooddelivery.dto.dish.DishDto;
import com.example.fooddelivery.service.DishService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/dishes")
@Tag(name = "Блюда", description = "Управление позициями меню")
public class DishController {
    private final DishService dishService;

    @GetMapping
    @Operation(summary = "Получить все блюда",
            description = "Фильтрация по имени или цене. По умолчанию сортировка по убыванию")
    public Page<DishDto> getAllDishes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "price", required = false) Double price) {
        log.info("request to get all dishes for page: {} and size: {}", page, size);
        if (name != null) {
            Pageable pageable = PageRequest.of(page, size, Sort.by("name").descending());
            return dishService.findDishByName(name, pageable);
        }
        if (price != null) {
            Pageable pageable = PageRequest.of(page, size, Sort.by("price").descending());
            return dishService.findDishByPrice(price, pageable);
        }
        Pageable pageable = PageRequest.of(page, size);
        return dishService.findAllDishes(pageable);
    }

    @GetMapping("/dishes/{id:\\d+}")
    @Operation(summary = "Получить блюдо по ID")
    public DishDto getDishById(@PathVariable Long id) {
        log.info("request to get dish by id {}", id);
        return dishService.findDishById(id);
    }

    @PostMapping
    @Operation(summary = "Создать новое блюдо",
            description = "Добавляет новое блюдо с привязкой к Menu и Category")
    public DishDto createDish(@Valid @RequestBody DishCreateDto dishCreateDto) {
        log.info("request to create dish with name: {}", dishCreateDto.getName());
        return dishService.addDish(dishCreateDto);
    }

    @PutMapping("/{id:\\d+}")
    @Operation(summary = "Изменить блюдо")
    public DishDto updateDishById(@PathVariable Long id, @Valid @RequestBody DishCreateDto dishCreateDto) {
        log.info("request to update dish with id: {}, name: {}", id, dishCreateDto.getName());
        return dishService.updateDishById(id, dishCreateDto);
    }

    @DeleteMapping("/{id:\\d+}")
    @Operation(summary = "Удалить блюдо")
    public void deleteDishById(@PathVariable Long id) {
        log.info("request to delete dish with id: {}", id);
        dishService.deleteDishById(id);
    }
}
