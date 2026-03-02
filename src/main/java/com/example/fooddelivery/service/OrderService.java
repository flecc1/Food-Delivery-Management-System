package com.example.fooddelivery.service;

import com.example.fooddelivery.dto.order.OrderCreateDto;
import com.example.fooddelivery.dto.order.OrderDto;
import com.example.fooddelivery.entity.Customer;
import com.example.fooddelivery.entity.Dish;
import com.example.fooddelivery.entity.Order;
import com.example.fooddelivery.exception.CustomerNotFoundException;
import com.example.fooddelivery.exception.OrderNotFoundException;
import com.example.fooddelivery.mapper.OrderMapper;
import com.example.fooddelivery.repository.CustomerRepository;
import com.example.fooddelivery.repository.DishRepository;
import com.example.fooddelivery.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final DishRepository dishRepository;
    private final CustomerRepository customerRepository;


    public OrderDto findOrderById(Long id) {
        return orderRepository.findById(id)
                .map(orderMapper::toOrderDto)
                .orElseThrow(() -> new OrderNotFoundException("Order #" + id + " not found"));
    }

    public List<OrderDto> getOrders() {
        return orderRepository.findAll()
                .stream()
                .map(orderMapper::toOrderDto)
                .toList();
    }

    @Transactional
    public OrderDto addOrder(OrderCreateDto newOrderDto) {
        Order order = orderMapper.toEntity(newOrderDto);
        Customer customer = customerRepository.findById(newOrderDto.getCustomerId())
                .orElseThrow(() -> new CustomerNotFoundException("Customer with id " + newOrderDto.getCustomerId() + " not found"));
        order.setCustomer(customer);
        List<Dish> dishes = dishRepository.findAllById(newOrderDto.getDishesId());
        order.setDishes(dishes);
        double totalPrice = dishes.stream().mapToDouble(Dish::getPrice).sum();
        order.setPrice(totalPrice);
        order.setAddress(newOrderDto.getAddress());
        order.setStatus("CREATED");
        order.setCreatedAt(LocalDateTime.now());
        order.setAmount(dishes.size());
        orderRepository.save(order);
        return orderMapper.toOrderDto(order);
    }

    @Transactional
    public OrderDto updateOrder(Long id, OrderCreateDto newOrder) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Cannot update: Order #" + id + " not found"));
        order.setAddress(newOrder.getAddress());
        List<Dish> dishes = dishRepository.findAllById(newOrder.getDishesId());
        order.setDishes(dishes);

        double totalPrice = dishes.stream().mapToDouble(Dish::getPrice).sum();
        order.setPrice(totalPrice);
        order.setAmount(dishes.size());
        return orderMapper.toOrderDto(orderRepository.save(order));
    }

    @Transactional
    public void deleteOrder(Long id) {
        if(!orderRepository.existsById(id)) {
            throw new OrderNotFoundException("Order not found");
        }
        orderRepository.deleteById(id);
    }

}
