package com.example.fooddelivery.controller;

import com.example.fooddelivery.dto.order.OrderCreateDto;
import com.example.fooddelivery.dto.order.OrderDto;
import com.example.fooddelivery.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/{id:\\d+}")
    public OrderDto getOrder(@PathVariable Long id) {
        return orderService.findOrderById(id);
    }

    @GetMapping("/search")
    public Page<OrderDto> getOrdersByCustomerLastName(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(value = "lastName", required = false) String lastName) {
        Pageable pageable = (lastName != null)
                ? PageRequest.of(page, size, Sort.by("customer.lastName").descending())
                : PageRequest.of(page, size, Sort.by("createdAt").descending());
        return orderService.getOrders(lastName, pageable);
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
