package com.example.fooddelivery.dto.order;

import com.example.fooddelivery.dto.dish.DishDto;
import com.example.fooddelivery.status.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Schema(description = "Информация о заказе")
public class OrderDto {
    @Schema(description = "ID заказа", example = "5001")
    private Long id;
    @Schema(description = "Текущий статус")
    private OrderStatus status;
    @Schema(description = "Дата и время создания")
    private LocalDateTime createdAt;
    @Schema(description = "Общее количество позиций в заказе", example = "3")
    private int amount;
    @Schema(description = "Список блюд в заказе")
    private List<DishDto> dishes;
    @Schema(description = "Итоговая стоимость", example = "75.50")
    private double totalPrice;
    @Schema(description = "Адрес доставки", example = "ул. Мира, 15")
    private String address;
    @Schema(description = "ID клиента", example = "1")
    private Long customerId;
    @Schema(description = "Имя клиента", example = "Иван")
    private String customerFirstName;
    @Schema(description = "Фамилия клиента", example = "Иванов")
    private String customerLastName;
}