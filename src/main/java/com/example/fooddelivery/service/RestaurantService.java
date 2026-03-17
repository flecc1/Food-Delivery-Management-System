package com.example.fooddelivery.service;

import com.example.fooddelivery.dto.restaurant.RestaurantCreateDto;
import com.example.fooddelivery.dto.restaurant.RestaurantShortDto;
import com.example.fooddelivery.entity.Restaurant;
import com.example.fooddelivery.exception.CategoryNotFoundException;
import com.example.fooddelivery.exception.RestaurantHasOrdersException;
import com.example.fooddelivery.exception.RestaurantNotFoundException;
import com.example.fooddelivery.mapper.RestaurantMapper;
import com.example.fooddelivery.repository.OrderRepository;
import com.example.fooddelivery.repository.RestaurantRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper restaurantMapper;
    private final OrderRepository orderRepository;

    public RestaurantShortDto findRestaurantById(Long id) {
        return restaurantRepository.findById(id)
                .map(restaurantMapper::toShortDto)
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant with id " + id + " not found"));
    }

    public Page<RestaurantShortDto> getRestaurants(Pageable pageable) {
        return restaurantRepository.findAll(pageable)
                .map(restaurantMapper::toShortDto);
    }

    public Page<RestaurantShortDto> findByName(String name, Pageable pageable) {
        return restaurantRepository.findByName(name, pageable)
                .map(restaurantMapper::toShortDto);
    }

    public Page<RestaurantShortDto> findByCategoryName(String categoryName, Pageable pageable) {
        if (categoryName == null) {
            throw new CategoryNotFoundException("Category name cannot be empty");
        }
        Page<Restaurant> restaurants = restaurantRepository.findByDishCategory(categoryName, pageable);
        if (restaurants.isEmpty()) {
            throw new RestaurantNotFoundException("Restaurant category with name " + categoryName + " not found");
        }
        return restaurants.map(restaurantMapper::toShortDto);
    }

    @Transactional
    public RestaurantShortDto addRestaurant(RestaurantCreateDto restaurantCreateDto) {
        Restaurant restaurant = restaurantMapper.toEntity(restaurantCreateDto);
        restaurantRepository.save(restaurant);
        return restaurantMapper.toShortDto(restaurant);
    }

    @Transactional
    public RestaurantShortDto updateRestaurant(Long id, RestaurantCreateDto newRestaurant) {
        Restaurant saved = restaurantRepository.findById(id)
                .orElseThrow();
        saved.setName(newRestaurant.getName());
        saved.setAddress(newRestaurant.getAddress());
        saved.setCity(newRestaurant.getCity());
        return restaurantMapper.toShortDto(restaurantRepository.save(saved));
    }

    @Transactional
    public void deleteRestaurant(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant with id " + id + " not found"));
        if (!orderRepository.getByRestaurantId(restaurant.getId()).isEmpty()) {
            throw new RestaurantHasOrdersException("Restaurant has orders and cannot be delete");
        }
        restaurantRepository.delete(restaurant);
    }
}
