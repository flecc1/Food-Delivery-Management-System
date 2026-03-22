package com.example.fooddelivery.controller;

import com.example.fooddelivery.dto.order.OrderCreateDto;
import com.example.fooddelivery.dto.order.OrderDto;
import com.example.fooddelivery.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
@Tag(name = "Заказы", description = "Создание и обработка заказов")
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/{id:\\d+}")
    @Operation(summary = "Получить информацию о заказе по ID")
    public OrderDto getOrder(@PathVariable Long id) {
        log.info("request to get Order with id {}", id);
        return orderService.findOrderById(id);
    }

    @GetMapping("/search")
    @Operation(summary = "Список заказов с фильтром",
            description = "Поиск по фамилии клиента. Сортировка по дате создания")
    public Page<OrderDto> getOrdersByCustomerLastName(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(value = "lastName", required = false) String lastName) {
        log.info("request to get Orders by lastName {}, page: {}, size: {}", lastName, page, size);
        Pageable pageable = (lastName != null)
                ? PageRequest.of(page, size, Sort.by("customer.lastName").descending())
                : PageRequest.of(page, size, Sort.by("createdAt").descending());
        return orderService.getOrders(lastName, pageable);
    }

    @PostMapping
    @Operation(summary = "Оформить новый заказ")
    public OrderDto createOrder(@Valid @RequestBody OrderCreateDto orderCreateDto) {
        log.info("request to create order with restaurant id: {}", orderCreateDto.getRestaurantId());
        return orderService.addOrder(orderCreateDto);
    }

    @PutMapping("/{id:\\d+}")
    @Operation(summary = "Обновить статус или данные заказа")
    public OrderDto updateOrder(@PathVariable Long id, @Valid @RequestBody OrderCreateDto order) {
        log.info("request to update order with id {}", id);
        return orderService.updateOrder(id, order);
    }

    @DeleteMapping("/{id:\\d+}")
    @Operation(summary = "Отменить/Удалить заказ")
    public void deleteOrder(@PathVariable Long id) {
        log.info("request to delete order with id {}", id);
        orderService.deleteOrder(id);
    }
}
