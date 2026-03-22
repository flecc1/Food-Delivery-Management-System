package com.example.fooddelivery.controller;

import com.example.fooddelivery.dto.restaurant.RestaurantCreateDto;
import com.example.fooddelivery.dto.restaurant.RestaurantShortDto;
import com.example.fooddelivery.service.RestaurantService;
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

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/restaurants")
public class RestaurantController {
    private final RestaurantService restaurantService;

    @GetMapping("/{id:\\d+}")
    public RestaurantShortDto getRestaurantsById(@PathVariable Long id) {
        log.info("request to get restaurant with id {}", id);
        return restaurantService.findRestaurantById(id);
    }

    @GetMapping("/search-by-name")
    public Page<RestaurantShortDto> getRestaurantsByName(
            @RequestParam(value = "name") String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("request to get restaurant by name {}, page: {}, size: {}", name, page, size);
        Pageable pageable = PageRequest.of(page, size);
        return restaurantService.findByName(name, pageable);
    }

    @GetMapping
    public Page<RestaurantShortDto> getAllRestaurants(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("request to get all restaurants page: {}, size: {}", page, size);
        Pageable pageable = PageRequest.of(page, size);
        return restaurantService.getRestaurants(pageable);
    }

    @GetMapping("/search")
    public Page<RestaurantShortDto> findByCategory(
            @RequestParam(value = "categoryName") String categoryName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("request to find restaurant by category name {}, page: {}, size: {}", categoryName, page, size);
        Pageable pageable = PageRequest.of(page, size);
        return restaurantService.findByCategoryName(categoryName, pageable);
    }

    @PostMapping
    public RestaurantShortDto createRestaurant(@Valid @RequestBody RestaurantCreateDto restaurantCreateDto) {
        log.info("request to create restaurant with name: {}", restaurantCreateDto.getName());
        return restaurantService.addRestaurant(restaurantCreateDto);
    }

    @PutMapping("/{id:\\d+}")
    public RestaurantShortDto updateRestaurant(
            @PathVariable Long id,
            @Valid @RequestBody RestaurantCreateDto restaurantDto) {
        log.info("request to update restaurant with id {}, name: {}", id, restaurantDto.getName());
        return restaurantService.updateRestaurant(id, restaurantDto);
    }

    @DeleteMapping("/{id:\\d+}")
    public void deleteRestaurant(@PathVariable Long id) {
        log.info("request to delete restaurant with id {}", id);
        restaurantService.deleteRestaurant(id);
    }
}