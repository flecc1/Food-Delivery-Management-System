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
import com.example.fooddelivery.status.OrderStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private static final String NOT_FOUND_SUFFIX = " not found";
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final DishRepository dishRepository;
    private final CustomerRepository customerRepository;

    public OrderDto findOrderById(Long id) {
        return orderRepository.findWithDishesAndCustomerById(id)
                .map(orderMapper::toOrderDto)
                .orElseThrow(() -> new OrderNotFoundException("Order with id " + id + NOT_FOUND_SUFFIX));
    }

    public List<OrderDto> getOrders() {
        return orderRepository.findAll()
                .stream()
                .map(orderMapper::toOrderDto)
                .toList();
    }

    public List<OrderDto> findByCustomerLastName(String lastName) {
        if (lastName == null) {
            throw new CustomerNotFoundException("lastName cannot be empty");
        }
        List<Order> orders = orderRepository.findByCustomerLastName(lastName);
        if (orders.isEmpty()) {
            throw new OrderNotFoundException("Order with customers lastName: " + lastName + NOT_FOUND_SUFFIX);
        }
        return orders
                .stream()
                .map(orderMapper::toOrderDto)
                .toList();
    }

    @Transactional
    public OrderDto addOrder(OrderCreateDto newOrderDto) {
        if (newOrderDto.getDishesId() == null || newOrderDto.getDishesId().isEmpty()) {
            throw new IllegalArgumentException("The list of dishes cannot be empty");
        }
        Order order = orderMapper.toEntity(newOrderDto);
        Customer customer = customerRepository.findById(newOrderDto.getCustomerId())
                .orElseThrow(() -> new CustomerNotFoundException("Customer with id "
                        + newOrderDto.getCustomerId() + NOT_FOUND_SUFFIX));
        order.setCustomer(customer);
        List<Dish> dishes = dishRepository.findAllById(newOrderDto.getDishesId());
        order.setDishes(dishes);
        double totalPrice = dishes.stream().mapToDouble(Dish::getPrice).sum();
        order.setTotalPrice(totalPrice);
        order.setAddress(newOrderDto.getAddress());
        order.setStatus(OrderStatus.CREATED);
        order.setCreatedAt(LocalDateTime.now());
        order.setAmount(dishes.size());
        orderRepository.save(order);
        return orderMapper.toOrderDto(order);
    }

    @Transactional
    public OrderDto updateOrder(Long id, OrderCreateDto newOrder) {
        Order order = orderRepository.findWithDishesAndCustomerById(id)
                .orElseThrow(() -> new OrderNotFoundException("Cannot update: Order id " + id + NOT_FOUND_SUFFIX));
        order.setAddress(newOrder.getAddress());
        List<Dish> dishes = dishRepository.findAllById(newOrder.getDishesId());
        order.setDishes(dishes);

        Customer customer = customerRepository.findById(newOrder.getCustomerId())
                .orElseThrow(() -> new CustomerNotFoundException("Customer with id "
                        + newOrder.getCustomerId() + NOT_FOUND_SUFFIX));
        order.setCustomer(customer);
        double totalPrice = dishes.stream().mapToDouble(Dish::getPrice).sum();
        order.setTotalPrice(totalPrice);
        order.setAmount(dishes.size());
        order.setStatus(OrderStatus.CREATED);
        order.setCreatedAt(LocalDateTime.now());
        return orderMapper.toOrderDto(orderRepository.save(order));
    }

    @Transactional
    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new OrderNotFoundException("Order with id " + id + NOT_FOUND_SUFFIX);
        }
        orderRepository.deleteById(id);
    }
}
