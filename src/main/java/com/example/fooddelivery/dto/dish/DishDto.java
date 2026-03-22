package com.example.fooddelivery.dto.dish;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(description = "Полная информация о блюде")
public class DishDto {
    @Schema(description = "ID блюда", example = "101")
    private Long id;

    @Schema(description = "Название", example = "Пеперони")
    private String name;

    @Schema(description = "Цена", example = "25.0")
    private double price;

    @Schema(description = "Описание", example = "Острая колбаса, моцарелла, томатный соус")
    private String description;

    @Schema(description = "Название категории", example = "Пицца")
    private String categoryName;

    @Schema(description = "ID ресторана", example = "5")
    private Long restaurantId;

    @Schema(description = "Название ресторана", example = "Papa Johns")
    private String restaurantName;
}
