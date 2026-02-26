package com.example.fooddelivery.controller;

import com.example.fooddelivery.dto.restaurant.RestaurantCreateDto;
import com.example.fooddelivery.dto.restaurant.RestaurantShortDto;
import com.example.fooddelivery.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public RestaurantShortDto createRestaurant(@RequestBody RestaurantCreateDto restaurantCreateDto) {
        return restaurantService.addRestaurant(restaurantCreateDto);
    }

    @PutMapping("/{id:\\d+}")
    public RestaurantShortDto updateRestaurant(@PathVariable Long id, @RequestBody RestaurantCreateDto restaurantCreateDto) {
        return restaurantService.updateRestaurant(id, restaurantCreateDto);
    }

    @DeleteMapping("/{id:\\d+}")
    public void deleteRestaurant(@PathVariable Long id) {
        restaurantService.deleteRestaurant(id);
    }
}