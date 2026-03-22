package com.example.fooddelivery.service;

import com.example.fooddelivery.cache.OrderCacheKey;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private static final String NOT_FOUND_SUFFIX = " not found";
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final DishRepository dishRepository;
    private final CustomerRepository customerRepository;
    private final HashMap<OrderCacheKey, Page<OrderDto>> orderCache = new HashMap<>();

    public OrderDto findOrderById(Long id) {
        log.debug("try find order with id: {}", id);
        OrderDto orderDto = orderRepository.findWithDishesAndCustomerById(id)
                .map(orderMapper::toOrderDto)
                .orElseThrow(() -> new OrderNotFoundException("Order with id " + id + NOT_FOUND_SUFFIX));
        log.info("order found with id: {} successfully", id);
        return orderDto;
    }

    public Page<OrderDto> getOrders(String lastName, Pageable pageable) {
        log.debug("try find orders by users last name: {}", lastName);
        OrderCacheKey key = new OrderCacheKey(
                lastName,
                pageable.getPageNumber(),
                pageable.getPageSize(),
                pageable.getSort());
        log.debug("check cache for key (lastName: {}, page number: {}, page size: {})",
                key, pageable.getPageNumber(), pageable.getPageSize());
        if (orderCache.containsKey(key)) {
            log.info("cache: return from HashMap");
            return orderCache.get(key);
        }
        log.info("fetching orders from data base");
        Page<Order> orders;
        log.debug("request contains last name: {}", lastName);
        if (lastName != null) {
            log.debug("try find orders by users last name: {}", lastName);
            orders = orderRepository.findByCustomerLastName(lastName, pageable);
            if (orders.isEmpty()) {
                log.warn("search failed no orders found for last name: {}", lastName);
                throw new OrderNotFoundException("Order with customers lastName: " + lastName + NOT_FOUND_SUFFIX);
            }
        } else {
            log.debug("fetching all orders from data base");
            orders = orderRepository.findAll(pageable);
        }

        Page<OrderDto> ordersDto = orders.map(orderMapper::toOrderDto);
        orderCache.put(key, ordersDto);
        log.info("save to cache successfully");
        return ordersDto;
    }

    @Transactional
    public OrderDto addOrder(OrderCreateDto newOrderDto) {
        log.debug("try adding new order for customer with id: {}", newOrderDto.getCustomerId());
        if (newOrderDto.getDishesId() == null || newOrderDto.getDishesId().isEmpty()) {
            log.warn("add order failed list of dishes is empty");
            throw new IllegalArgumentException("The list of dishes cannot be empty");
        }
        Order order = orderMapper.toEntity(newOrderDto);
        log.debug("try to find customer with id: {}", newOrderDto.getCustomerId());
        Customer customer = customerRepository.findById(newOrderDto.getCustomerId())
                .orElseThrow(() -> new CustomerNotFoundException("Customer with id "
                        + newOrderDto.getCustomerId() + NOT_FOUND_SUFFIX));
        order.setCustomer(customer);
        log.debug("try to find dishes with ids: {}", newOrderDto.getDishesId());
        List<Dish> dishes = dishRepository.findAllById(newOrderDto.getDishesId());
        order.setDishes(dishes);
        double totalPrice = dishes.stream().mapToDouble(Dish::getPrice).sum();
        order.setTotalPrice(totalPrice);
        order.setAddress(newOrderDto.getAddress());
        order.setStatus(OrderStatus.CREATED);
        order.setCreatedAt(LocalDateTime.now());
        order.setAmount(dishes.size());
        Order saved = orderRepository.save(order);
        log.debug("clearing order cache after adding new order");
        orderCache.clear();
        log.info("order added with id: {} successfully, total price: {}", saved.getId(), totalPrice);
        return orderMapper.toOrderDto(saved);
    }

    @Transactional
    public OrderDto updateOrder(Long id, OrderCreateDto newOrder) {
        log.debug("try updating order with id: {}", id);
        log.debug("finding order with id: {}", id);
        Order order = orderRepository.findWithDishesAndCustomerById(id)
                .orElseThrow(() -> new OrderNotFoundException("Cannot update: Order id " + id + NOT_FOUND_SUFFIX));
        order.setAddress(newOrder.getAddress());
        log.debug("try find dishes with ids: {}", newOrder.getDishesId());
        List<Dish> dishes = dishRepository.findAllById(newOrder.getDishesId());
        order.setDishes(dishes);

        log.debug("try to fetch customer");
        Customer customer = customerRepository.findById(newOrder.getCustomerId())
                .orElseThrow(() -> new CustomerNotFoundException("Customer with id "
                        + newOrder.getCustomerId() + NOT_FOUND_SUFFIX));
        order.setCustomer(customer);
        double totalPrice = dishes.stream().mapToDouble(Dish::getPrice).sum();
        order.setTotalPrice(totalPrice);
        order.setAmount(dishes.size());
        order.setStatus(OrderStatus.CREATED);
        order.setCreatedAt(LocalDateTime.now());
        orderRepository.save(order);
        log.debug("clearing order cache after updating");
        orderCache.clear();
        log.info("order updated with id: {} successfully, total price: {}", order.getId(), totalPrice);
        return orderMapper.toOrderDto(order);
    }

    @Transactional
    public void deleteOrder(Long id) {
        log.debug("try delete order with id: {}", id);
        log.debug("try find order by id: {}", id);
        if (!orderRepository.existsById(id)) {
            log.warn("order not found with id: {}", id);
            throw new OrderNotFoundException("Order with id " + id + NOT_FOUND_SUFFIX);
        }
        orderRepository.deleteById(id);
        log.info("delete order with id: {} successfully", id);
        orderCache.clear();
        log.info("clearing order cache after deleting order with id: {}", id);
    }
}
