package com.example.fooddelivery.service;

import com.example.fooddelivery.dto.RestaurantShortDto;
import com.example.fooddelivery.entity.Restaurant;
import com.example.fooddelivery.mapper.RestaurantMapper;
import com.example.fooddelivery.repository.RestaurantRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper restaurantMapper;

    public RestaurantService(RestaurantRepository restaurantRepository, RestaurantMapper restaurantMapper) {
        this.restaurantRepository = restaurantRepository;
        this.restaurantMapper = restaurantMapper;
    }

    public List<RestaurantShortDto> getRestaurants(){
        List<Restaurant> foundRestaurants = restaurantRepository.findAll();
        List<RestaurantShortDto> restaurantShortDtos = new ArrayList<>();
        for (Restaurant res:foundRestaurants){
            restaurantShortDtos.add(restaurantMapper.toShortDto(res));
        }
        return restaurantShortDtos;
    }
}
