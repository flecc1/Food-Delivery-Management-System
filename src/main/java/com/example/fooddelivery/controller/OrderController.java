package com.example.fooddelivery.controller;

import com.example.fooddelivery.dto.order.OrderCreateDto;
import com.example.fooddelivery.dto.order.OrderDto;
import com.example.fooddelivery.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/{id:\\d+}")
    public OrderDto getOrder(@PathVariable Long id) {
        return orderService.findOrderById(id);
    }

    @GetMapping
    public List<OrderDto> getOrders() {
        return orderService.getOrders();
    }

    @GetMapping("/search")
    public List<OrderDto> getOrdersByCustomerLastName(
            @RequestParam(value = "lastName") String lastName) {
        return orderService.findByCustomerLastName(lastName);
    }
    @PostMapping
    public OrderDto createOrder(@RequestBody OrderCreateDto orderCreateDto) {
        return orderService.addOrder(orderCreateDto);
    }

    @PutMapping("/{id:\\d+}")
    public OrderDto updateOrder(@PathVariable Long id, @RequestBody OrderCreateDto order) {
        return orderService.updateOrder(id, order);
    }

    @DeleteMapping("/{id:\\d+}")
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }
}
