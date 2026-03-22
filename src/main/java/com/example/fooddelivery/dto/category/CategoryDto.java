package com.example.fooddelivery.dto.category;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(description = "Информация о категории")
public class CategoryDto {
    @Schema(description = "ID категории", example = "1")
    private Long id;

    @Schema(description = "Название категории", example = "Напитки")
    private String name;

    @Schema(description = "Количество блюд, привязанных к этой категории", example = "12")
    private int dishCount;
}
