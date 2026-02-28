package com.example.fooddelivery.controller;

import com.example.fooddelivery.dto.dish.DishCreateDto;
import com.example.fooddelivery.dto.dish.DishDto;
import com.example.fooddelivery.service.DishService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/dishes")
public class DishController {
    private final DishService dishService;

    @GetMapping
    public List<DishDto> getDishes() {
        return dishService.findAllDishes();
    }
    @GetMapping("/{id:\\d+}")
    public DishDto getDishById(@PathVariable Long id) {
        return dishService.findDishById(id);
    }
    @GetMapping(value = "name")
    public List<DishDto> getDishByName(@RequestParam(value = "name") String name) {
        return dishService.findDishByName(name);
    }
    @GetMapping(value = "price")
    public List<DishDto> getDishByPrice(@RequestParam(value = "price") double price) {
        return dishService.findDishByPrice(price);
    }
    @PostMapping
    public DishDto createDish(@RequestBody DishCreateDto dishCreateDto) {
        return dishService.addDish(dishCreateDto);
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
