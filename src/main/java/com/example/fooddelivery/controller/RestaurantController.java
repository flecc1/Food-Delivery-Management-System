package com.example.fooddelivery.controller;

import com.example.fooddelivery.dto.RestaurantShortDto;
import com.example.fooddelivery.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/restaurants")
public class RestaurantController {
    private final RestaurantService restaurantService;
    @GetMapping("/{id:\\d+}")
    public RestaurantShortDto getRestaurants(@PathVariable Long id) {
        return restaurantService.findRestaurantById(id);
    }

    @GetMapping
    public List<RestaurantShortDto> getRestaurantsByName(@RequestParam(value = "name", required = false) String name) {
        return name == null
                ? restaurantService.getRestaurants()
                : restaurantService.findByName(name);
    }
}