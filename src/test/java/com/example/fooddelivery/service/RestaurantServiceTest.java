package com.example.fooddelivery.service;

import com.example.fooddelivery.dto.restaurant.RestaurantCreateDto;
import com.example.fooddelivery.dto.restaurant.RestaurantShortDto;
import com.example.fooddelivery.entity.Order;
import com.example.fooddelivery.entity.Restaurant;
import com.example.fooddelivery.exception.CategoryNotFoundException;
import com.example.fooddelivery.exception.RestaurantHasOrdersException;
import com.example.fooddelivery.exception.RestaurantNotFoundException;
import com.example.fooddelivery.mapper.RestaurantMapper;
import com.example.fooddelivery.repository.OrderRepository;
import com.example.fooddelivery.repository.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantServiceTest {

    @Mock
    private RestaurantRepository restaurantRepository;
    @Mock
    private RestaurantMapper restaurantMapper;
    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private RestaurantService restaurantService;

    private Restaurant restaurant;
    private RestaurantShortDto restaurantShortDto;
    private RestaurantCreateDto restaurantCreateDto;
    private Long restaurantId = 1L;

    @BeforeEach
    void setUp() {
        restaurant = new Restaurant();
        restaurant.setId(restaurantId);
        restaurant.setName("Test Restaurant");

        restaurantShortDto = new RestaurantShortDto();
        restaurantShortDto.setName("Test Restaurant");

        restaurantCreateDto = new RestaurantCreateDto();
        restaurantCreateDto.setName("New Name");
        restaurantCreateDto.setAddress("New Address");
        restaurantCreateDto.setCity("New City");
    }

    @Test
    void findRestaurantById_Success() {
        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(restaurant));
        when(restaurantMapper.toShortDto(restaurant)).thenReturn(restaurantShortDto);

        RestaurantShortDto result = restaurantService.findRestaurantById(restaurantId);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Test Restaurant");
    }

    @Test
    void findRestaurantById_NotFound_ThrowsException() {
        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> restaurantService.findRestaurantById(restaurantId))
                .isInstanceOf(RestaurantNotFoundException.class)
                .hasMessageContaining("Restaurant not found with id " + restaurantId);
    }

    @Test
    void getRestaurants_Success() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Restaurant> page = new PageImpl<>(List.of(restaurant));
        when(restaurantRepository.findAll(pageable)).thenReturn(page);
        when(restaurantMapper.toShortDto(any())).thenReturn(restaurantShortDto);

        Page<RestaurantShortDto> result = restaurantService.getRestaurants(pageable);

        assertThat(result.getContent()).hasSize(1);
        verify(restaurantRepository).findAll(pageable);
    }

    @Test
    void findByName_Success() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Restaurant> page = new PageImpl<>(List.of(restaurant));
        when(restaurantRepository.findByName("Test", pageable)).thenReturn(page);
        when(restaurantMapper.toShortDto(any())).thenReturn(restaurantShortDto);

        Page<RestaurantShortDto> result = restaurantService.findByName("Test", pageable);

        assertThat(result.getContent()).hasSize(1);
    }

    @Test
    void findByCategoryName_Success() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Restaurant> page = new PageImpl<>(List.of(restaurant));
        when(restaurantRepository.findByDishCategory("Pizza", pageable)).thenReturn(page);
        when(restaurantMapper.toShortDto(any())).thenReturn(restaurantShortDto);

        Page<RestaurantShortDto> result = restaurantService.findByCategoryName("Pizza", pageable);

        assertThat(result.getContent()).hasSize(1);
    }

    @Test
    void findByCategoryName_NullName_ThrowsException() {
        Pageable pageable = PageRequest.of(0, 10);

        assertThatThrownBy(() -> restaurantService.findByCategoryName(null, pageable))
                .isInstanceOf(CategoryNotFoundException.class);
    }

    @Test
    void findByCategoryName_EmptyResult_ThrowsException() {
        Pageable pageable = PageRequest.of(0, 10);
        when(restaurantRepository.findByDishCategory("Empty", pageable)).thenReturn(Page.empty());

        assertThatThrownBy(() -> restaurantService.findByCategoryName("Empty", pageable))
                .isInstanceOf(RestaurantNotFoundException.class);
    }

    @Test
    void addRestaurant_Success() {
        when(restaurantMapper.toEntity(restaurantCreateDto)).thenReturn(restaurant);
        when(restaurantRepository.save(any())).thenReturn(restaurant);
        when(restaurantMapper.toShortDto(any())).thenReturn(restaurantShortDto);

        RestaurantShortDto result = restaurantService.addRestaurant(restaurantCreateDto);

        assertThat(result).isNotNull();
        verify(restaurantRepository).save(restaurant);
    }

    @Test
    void updateRestaurant_Success() {
        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(restaurant));
        when(restaurantMapper.toShortDto(any())).thenReturn(restaurantShortDto);

        RestaurantShortDto result = restaurantService.updateRestaurant(restaurantId, restaurantCreateDto);

        assertThat(result).isNotNull();
        assertThat(restaurant.getName()).isEqualTo("New Name");
        verify(restaurantRepository).save(restaurant);
    }

    @Test
    void updateRestaurant_NotFound_ThrowsException() {
        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> restaurantService.updateRestaurant(restaurantId, restaurantCreateDto))
                .isInstanceOf(RestaurantNotFoundException.class);
    }

    @Test
    void deleteRestaurant_Success() {
        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(restaurant));
        when(orderRepository.getByRestaurantId(restaurantId)).thenReturn(Collections.emptyList());

        restaurantService.deleteRestaurant(restaurantId);

        verify(restaurantRepository).delete(restaurant);
    }

    @Test
    void deleteRestaurant_NotFound_ThrowsException() {
        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> restaurantService.deleteRestaurant(restaurantId))
                .isInstanceOf(RestaurantNotFoundException.class);
    }

    @Test
    void deleteRestaurant_WithOrders_ThrowsException() {
        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(restaurant));
        when(orderRepository.getByRestaurantId(restaurantId)).thenReturn(List.of(new Order()));

        assertThatThrownBy(() -> restaurantService.deleteRestaurant(restaurantId))
                .isInstanceOf(RestaurantHasOrdersException.class);

        verify(restaurantRepository, never()).delete(any());
    }
}