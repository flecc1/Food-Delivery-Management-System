package com.example.fooddelivery.dto.menu;

import com.example.fooddelivery.dto.dish.DishDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Schema(description = "Объект передачи данных для меню ресторана")
public class MenuDto {
    @Schema(description = "Уникальный идентификатор меню", example = "1")
    private Long id;
    @Schema(description = "Название меню", example = "Основное меню")
    private String name;
    @Schema(description = "Описание меню", example = "Популярные блюда европейской кухни")
    private String description;
    @Schema(description = "Статус активности меню", example = "true")
    private boolean active;
    @Schema(description = "Список блюд, входящих в данное меню")
    private List<DishDto> dishes;
    @Schema(description = "Название ресторана, которому принадлежит меню", example = "Пицца Темпо")
    private String restaurantName;
}
