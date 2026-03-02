package com.example.fooddelivery.mapper;

import com.example.fooddelivery.dto.order.OrderCreateDto;
import com.example.fooddelivery.dto.order.OrderDto;
import com.example.fooddelivery.entity.Customer;
import com.example.fooddelivery.entity.Order;
import com.example.fooddelivery.exception.CustomerNotFoundException;
import com.example.fooddelivery.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor

public class OrderMapper {
    private final DishMapper dishMapper;
    public OrderDto toOrderDto(Order order) {
        if (order == null) {
            return null;
        }
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setStatus(order.getStatus());
        orderDto.setCreatedAt(order.getCreatedAt());
        orderDto.setAmount(order.getAmount());
        orderDto.setDishes(order.getDishes()
                .stream()
                .map(dishMapper::toDto)
                .toList());
        orderDto.setPrice(order.getPrice());
        orderDto.setAddress(order.getAddress());
        if (order.getCustomer() != null) {
            orderDto.setCustomerId(order.getCustomer().getId());
        }

        return orderDto;
    }

    public Order toEntity(OrderCreateDto orderCreateDto) {
        if (orderCreateDto == null) {
            return null;
        }
        Order order = new Order();
        order.setAddress(orderCreateDto.getAddress());
        return order;
    }
}
