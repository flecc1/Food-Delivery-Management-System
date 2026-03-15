package com.example.fooddelivery.controller;

import com.example.fooddelivery.dto.restaurant.RestaurantCreateDto;
import com.example.fooddelivery.dto.restaurant.RestaurantShortDto;
import com.example.fooddelivery.service.RestaurantService;
import lombok.RequiredArgsConstructor;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/restaurants")
public class RestaurantController {
    private final RestaurantService restaurantService;

    @GetMapping("/{id:\\d+}")
    public RestaurantShortDto getRestaurants(@PathVariable Long id) {
        return restaurantService.findRestaurantById(id);
    }

    @GetMapping("/search-by-name")
    public Page<RestaurantShortDto> getRestaurantsByName(
            @RequestParam(value = "name") String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return restaurantService.findByName(name, pageable);
    }

    @GetMapping
    public Page<RestaurantShortDto> getAllRestaurants (
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return restaurantService.getRestaurants(pageable);
    }

    @GetMapping("/search")
    public List<RestaurantShortDto> findByCategory(@RequestParam(value = "categoryName") String categoryName) {
        return restaurantService.findByCategoryName(categoryName);
    }

    @PostMapping
    public RestaurantShortDto createRestaurant(@RequestBody RestaurantCreateDto restaurantCreateDto) {
        return restaurantService.addRestaurant(restaurantCreateDto);
    }

    @PutMapping("/{id:\\d+}")
    public RestaurantShortDto updateRestaurant(@PathVariable Long id, @RequestBody RestaurantCreateDto restaurantDto) {
        return restaurantService.updateRestaurant(id, restaurantDto);
    }

    @DeleteMapping("/{id:\\d+}")
    public void deleteRestaurant(@PathVariable Long id) {
        restaurantService.deleteRestaurant(id);
    }
}