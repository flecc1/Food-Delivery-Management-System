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
class OrderServiceTest {
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderMapper orderMapper;
    @Mock
    private DishRepository dishRepository;
    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private OrderService orderService;

    private Order order;
    private OrderDto orderDto;
    private OrderCreateDto orderCreateDto;
    private Customer customer;
    private Dish dish;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setId(1L);

        dish = new Dish();
        dish.setId(1L);
        dish.setPrice(100.0);

        order = new Order();
        order.setId(1L);

        orderDto = new OrderDto();
        orderDto.setId(1L);

        orderCreateDto = new OrderCreateDto();
        orderCreateDto.setCustomerId(1L);
        orderCreateDto.setDishesId(List.of(1L));
        orderCreateDto.setAddress("Test Street");
    }

    @Test
    void findOrderById_Success() {
        when(orderRepository.findWithDishesAndCustomerById(1L)).thenReturn(Optional.of(order));
        when(orderMapper.toOrderDto(order)).thenReturn(orderDto);

        OrderDto result = orderService.findOrderById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    void findOrderById_NotFound_ThrowsException() {
        when(orderRepository.findWithDishesAndCustomerById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> orderService.findOrderById(1L))
                .isInstanceOf(OrderNotFoundException.class);
    }

    @Test
    void getOrders_WithLastName_Success() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Order> page = new PageImpl<>(List.of(order));

        when(orderRepository.findByCustomerLastName("Ivanov", pageable)).thenReturn(page);
        when(orderMapper.toOrderDto(any(Order.class))).thenReturn(orderDto);

        Page<OrderDto> result = orderService.getOrders("Ivanov", pageable);
        Page<OrderDto> cachedResult = orderService.getOrders("Ivanov", pageable);

        assertThat(result.getContent()).hasSize(1);
        assertThat(cachedResult).isEqualTo(result);
        verify(orderRepository, times(1)).findByCustomerLastName(anyString(), any());
    }

    @Test
    void getOrders_NullLastName_Success() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Order> page = new PageImpl<>(List.of(order));

        when(orderRepository.findAll(pageable)).thenReturn(page);
        when(orderMapper.toOrderDto(any(Order.class))).thenReturn(orderDto);

        Page<OrderDto> result = orderService.getOrders(null, pageable);

        assertThat(result.getContent()).hasSize(1);
        verify(orderRepository).findAll(pageable);
    }

    @Test
    void getOrders_NotFound_ThrowsException() {
        Pageable pageable = PageRequest.of(0, 10);
        when(orderRepository.findByCustomerLastName("Unknown", pageable)).thenReturn(Page.empty());

        assertThatThrownBy(() -> orderService.getOrders("Unknown", pageable))
                .isInstanceOf(OrderNotFoundException.class);
    }

    @Test
    void addOrder_Success() {
        when(orderMapper.toEntity(orderCreateDto)).thenReturn(order);
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(dishRepository.findAllById(any())).thenReturn(List.of(dish));
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(orderMapper.toOrderDto(order)).thenReturn(orderDto);

        OrderDto result = orderService.addOrder(orderCreateDto);

        assertThat(result).isNotNull();
        verify(orderRepository).save(any());
    }

    @Test
    void addOrder_EmptyDishes_ThrowsException() {
        orderCreateDto.setDishesId(Collections.emptyList());

        assertThatThrownBy(() -> orderService.addOrder(orderCreateDto))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void addOrder_CustomerNotFound_ThrowsException() {
        when(orderMapper.toEntity(orderCreateDto)).thenReturn(order);
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> orderService.addOrder(orderCreateDto))
                .isInstanceOf(CustomerNotFoundException.class);
    }

    @Test
    void updateOrder_Success() {
        when(orderRepository.findWithDishesAndCustomerById(1L)).thenReturn(Optional.of(order));
        when(dishRepository.findAllById(any())).thenReturn(List.of(dish));
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(orderMapper.toOrderDto(any())).thenReturn(orderDto);

        OrderDto result = orderService.updateOrder(1L, orderCreateDto);

        assertThat(result).isNotNull();
        verify(orderRepository).save(order);
    }

    @Test
    void updateOrder_NotFound_ThrowsException() {
        when(orderRepository.findWithDishesAndCustomerById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> orderService.updateOrder(1L, orderCreateDto))
                .isInstanceOf(OrderNotFoundException.class);
    }

    @Test
    void updateOrder_CustomerNotFound_ThrowsException() {
        when(orderRepository.findWithDishesAndCustomerById(1L)).thenReturn(Optional.of(order));
        when(dishRepository.findAllById(any())).thenReturn(List.of(dish));
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> orderService.updateOrder(1L, orderCreateDto))
                .isInstanceOf(CustomerNotFoundException.class);
    }

    @Test
    void deleteOrder_Success() {
        when(orderRepository.existsById(1L)).thenReturn(true);

        orderService.deleteOrder(1L);

        verify(orderRepository).deleteById(1L);
    }

    @Test
    void deleteOrder_NotFound_ThrowsException() {
        when(orderRepository.existsById(1L)).thenReturn(false);
        assertThatThrownBy(() -> orderService.deleteOrder(1L))
                .isInstanceOf(OrderNotFoundException.class);
    }
}