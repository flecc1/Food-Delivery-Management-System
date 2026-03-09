package com.example.fooddelivery.service;

import com.example.fooddelivery.dto.restaurant.RestaurantCreateDto;
import com.example.fooddelivery.dto.restaurant.RestaurantShortDto;
import com.example.fooddelivery.entity.Restaurant;
import com.example.fooddelivery.exception.RestaurantHasOrdersException;
import com.example.fooddelivery.exception.RestaurantNotFoundException;
import com.example.fooddelivery.mapper.RestaurantMapper;
import com.example.fooddelivery.repository.MenuRepository;
import com.example.fooddelivery.repository.OrderRepository;
import com.example.fooddelivery.repository.RestaurantRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper restaurantMapper;
    private final OrderRepository orderRepository;
    private final MenuRepository menuRepository;

    public RestaurantShortDto findRestaurantById(Long id) {
        return restaurantRepository.findById(id)
                .map(restaurantMapper::toShortDto)
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant with id " + id + " not found"));
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
