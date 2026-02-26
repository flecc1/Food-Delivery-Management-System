package com.example.fooddelivery.mapper;

import com.example.fooddelivery.dto.OrderDto;
import com.example.fooddelivery.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

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
        return orderDto;
    }
}
