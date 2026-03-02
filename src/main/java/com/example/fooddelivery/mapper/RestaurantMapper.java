package com.example.fooddelivery.mapper;

import com.example.fooddelivery.dto.restaurant.RestaurantCreateDto;
import com.example.fooddelivery.dto.restaurant.RestaurantShortDto;
import com.example.fooddelivery.entity.Restaurant;
import org.springframework.stereotype.Component;

@Component
public class RestaurantMapper {
    public RestaurantShortDto toShortDto(Restaurant restaurant) {
        if (restaurant == null) {
            return null;
        }
        RestaurantShortDto resShortDto = new RestaurantShortDto();
        resShortDto.setId(restaurant.getId());
        resShortDto.setName(restaurant.getName());
        resShortDto.setRating(restaurant.getRating());
        return resShortDto;
    }

    public Restaurant toEntity(RestaurantCreateDto restaurantCreateDto) {
        if (restaurantCreateDto == null) {
            return null;
        }
        Restaurant restaurant = new Restaurant();
        restaurant.setName(restaurantCreateDto.getName());
        restaurant.setAddress(restaurantCreateDto.getAddress());
        restaurant.setCity(restaurantCreateDto.getCity());
        return restaurant;
    }

}
