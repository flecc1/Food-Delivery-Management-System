package com.example.fooddelivery.controller;

import com.example.fooddelivery.dto.RestaurantShortDto;
import com.example.fooddelivery.service.RestaurantService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/v1/restaurants")
public class RestaurantController {
    private final RestaurantService restaurantService;
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping
    public List<RestaurantShortDto> getRestaurants(){
        return restaurantService.getRestaurants();
    }
}
