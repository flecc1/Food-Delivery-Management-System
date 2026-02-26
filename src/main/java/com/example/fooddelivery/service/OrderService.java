package com.example.fooddelivery.service;

import com.example.fooddelivery.dto.OrderDto;
import com.example.fooddelivery.entity.Order;
import com.example.fooddelivery.exception.RestaurantNotFoundException;
import com.example.fooddelivery.mapper.DishMapper;
import com.example.fooddelivery.mapper.OrderMapper;
import com.example.fooddelivery.repository.OrderRepository;
import com.example.fooddelivery.repository.RestaurantRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final RestaurantRepository restaurantRepository;
    private final DishMapper dishMapper;

    public OrderDto findOrderById(Long id) {
        return orderRepository.findById(id)
                .map(orderMapper::toOrderDto)
                .orElseThrow();
    }

    public List<OrderDto> getOrders() {
        return orderRepository.findAll()
                .stream()
                .map(orderMapper::toOrderDto)
                .toList();
    }

    @Transactional
    public OrderDto addOrder(Order order) {
        Order saved = orderRepository.save(order);
        return orderMapper.toOrderDto(saved);
    }

    @Transactional
    public OrderDto updateOrder(Long id, Order newOrder) {
        Order saved = orderRepository.findById(id)
                .orElseThrow();
        saved.setStatus(newOrder.getStatus());
        saved.setId(newOrder.getId());
        saved.setAmount(newOrder.getAmount());
        saved.setCreatedAt(newOrder.getCreatedAt());
        saved.setDishes(newOrder.getDishes());
        return orderMapper.toOrderDto(orderRepository.save(saved));
    }

    @Transactional
    public void deleteRestaurant(Long id) {
        if(!restaurantRepository.existsById(id)) {
            throw new RestaurantNotFoundException("Restaurant not found");
        }
        restaurantRepository.deleteById(id);
    }

}
