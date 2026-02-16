package com.example.fooddelivery.service;

import com.example.fooddelivery.dto.RestaurantShortDto;
import com.example.fooddelivery.mapper.RestaurantMapper;
import com.example.fooddelivery.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper restaurantMapper;

    public RestaurantShortDto findRestaurantById(Long id) {
        return restaurantRepository.findById(id)
                .map(restaurantMapper::toShortDto)
                .orElseThrow();
    }

    public List<RestaurantShortDto> getRestaurants() {
        return restaurantRepository.findAll()
                .stream()
                .map(restaurantMapper::toShortDto)
                .toList();
    }

    public List<RestaurantShortDto> findByName(String name) {
        return restaurantRepository.findByName(name)
                .stream()
                .map(restaurantMapper::toShortDto)
                .toList();
    }
}
