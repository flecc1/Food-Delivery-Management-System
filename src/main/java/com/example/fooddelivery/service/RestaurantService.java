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
        log.info("try find restaurant with id: {}", id);
        RestaurantShortDto restaurantDto = restaurantRepository.findById(id)
                .map(restaurantMapper::toShortDto)
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant with id " + id + " not found"));
        log.info("restaurant found with id: {} and name: {} successfully", id, restaurantDto.getName());
        return restaurantDto;
    }

    public Page<RestaurantShortDto> getRestaurants(Pageable pageable) {
        log.debug("try to all getRestaurants");
        Page<RestaurantShortDto> page = restaurantRepository.findAll(pageable)
                .map(restaurantMapper::toShortDto);
        log.info("all restaurants found successfully");
        return page;
    }

    public Page<RestaurantShortDto> findByName(String name, Pageable pageable) {
        log.debug("try to find restaurant with name: {}", name);
        Page<RestaurantShortDto> page = restaurantRepository.findByName(name, pageable)
                .map(restaurantMapper::toShortDto);
        log.info("all restaurants was found successfully");
        return page;
    }

    public Page<RestaurantShortDto> findByCategoryName(String categoryName, Pageable pageable) {
        log.debug("try to find restaurant with exist category : '{}'", categoryName);
        if (categoryName == null) {
            log.warn("search failed category name is null");
            throw new CategoryNotFoundException("Category name cannot be empty");
        }
        log.debug("try to find restaurant by category : '{}'", categoryName);
        Page<Restaurant> restaurants = restaurantRepository.findByDishCategory(categoryName, pageable);
        if (restaurants.isEmpty()) {
            log.warn("search failed: no restaurants found for category '{}'", categoryName);
            throw new RestaurantNotFoundException("Restaurant category with name " + categoryName + " not found");
        }
        Page<RestaurantShortDto> page = restaurants.map(restaurantMapper::toShortDto);
        log.info("restaurants with category: '{}' found successfully", categoryName);
        return page;
    }

    @Transactional
    public RestaurantShortDto addRestaurant(RestaurantCreateDto restaurantCreateDto) {
        log.debug("try to add restaurant with dto: {}", restaurantCreateDto);
        Restaurant restaurant = restaurantMapper.toEntity(restaurantCreateDto);
        restaurantRepository.save(restaurant);
        log.info("restaurant with id: {} add successfully", restaurant.getId());
        return restaurantMapper.toShortDto(restaurant);
    }

    @Transactional
    public RestaurantShortDto updateRestaurant(Long id, RestaurantCreateDto newRestaurant) {
        log.debug("try to update restaurant with id: {} and name: {}", id, newRestaurant.getName());
        Restaurant saved = restaurantRepository.findById(id)
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant with id " + id + " not found"));
        saved.setName(newRestaurant.getName());
        saved.setAddress(newRestaurant.getAddress());
        saved.setCity(newRestaurant.getCity());

        RestaurantShortDto restaurantDto = restaurantMapper.toShortDto(saved);
        restaurantRepository.save(saved);
        log.info("restaurant with id: {} updated successfully", id);
        return restaurantDto;
    }

    @Transactional
    public void deleteRestaurant(Long id) {
        log.debug("try to delete restaurant with id: {}", id);
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant with id " + id + " not found"));
        log.debug("check if restaurant with id: {} has orders", id);
        if (!orderRepository.getByRestaurantId(restaurant.getId()).isEmpty()) {
            log.warn("delete failed restaurant with id: {} has orders", id);
            throw new RestaurantHasOrdersException("Restaurant with id " + id + "has orders and cannot be delete");
        }
        restaurantRepository.delete(restaurant);
        log.info("restaurant with id: {} delete successfully", id);
    }
}
