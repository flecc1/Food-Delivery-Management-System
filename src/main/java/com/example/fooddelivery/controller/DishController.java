package com.example.fooddelivery.controller;

import com.example.fooddelivery.dto.dish.DishCreateDto;
import com.example.fooddelivery.dto.dish.DishDto;
import com.example.fooddelivery.service.DishService;
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
@RequiredArgsConstructor
@RequestMapping("/api/v1/dishes")
public class DishController {
    private final DishService dishService;

    @GetMapping
    public List<DishDto> getAllDishes(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "price", required = false) Double price) {
        if (name != null) {
            return dishService.findDishByName(name);
        }
        if (price != null) {
            return dishService.findDishByPrice(price);
        }
        return dishService.findAllDishes();
    }

    @GetMapping("/dishes/{id:\\d+}")
    public DishDto getDishById(@PathVariable Long id) {
        return dishService.findDishById(id);
    }

    @PostMapping
    public DishDto createDish(@RequestParam Long menuId, @RequestBody DishCreateDto dishCreateDto) {
        return dishService.addDish(menuId, dishCreateDto);
    }

    @PutMapping("/{id:\\d+}")
    public DishDto updateDishById(@PathVariable Long id, @RequestBody DishCreateDto dishCreateDto) {
        return dishService.updateDishById(id, dishCreateDto);
    }

    @DeleteMapping("/{id:\\d+}")
    public void deleteDishById(@PathVariable Long id) {
        dishService.deleteDishById(id);
    }
}
