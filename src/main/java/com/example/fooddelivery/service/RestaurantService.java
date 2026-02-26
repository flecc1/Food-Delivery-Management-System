package com.example.fooddelivery.service;

import com.example.fooddelivery.dto.RestaurantShortDto;
import com.example.fooddelivery.entity.Restaurant;
import com.example.fooddelivery.exception.ResourceNotFoundException;
import com.example.fooddelivery.exception.RestaurantNotFoundException;
import com.example.fooddelivery.mapper.RestaurantMapper;
import com.example.fooddelivery.repository.RestaurantRepository;
import jakarta.transaction.Transactional;
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

    @Transactional
    public RestaurantShortDto addRestaurant(Restaurant restaurant) {
        Restaurant saved = restaurantRepository.save(restaurant);
        return restaurantMapper.toShortDto(saved);
    }

    @Transactional
    public RestaurantShortDto updateRestaurant(Long id, Restaurant newRestaurant) {
        Restaurant saved = restaurantRepository.findById(id)
                .orElseThrow();
        saved.setName(newRestaurant.getName());
        saved.setId(newRestaurant.getId());
        saved.setRating(newRestaurant.getRating());
        return restaurantMapper.toShortDto(restaurantRepository.save(saved));
    }

    @Transactional
    public void deleteRestaurant(Long id) {
    if(!restaurantRepository.existsById(id)) {
        throw new RestaurantNotFoundException("Restaurant not found");
    }
        restaurantRepository.deleteById(id);
    }
}
