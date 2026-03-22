package com.example.fooddelivery.dto.menu;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Schema(description = "Данные для создания меню ресторана")
public class MenuCreateDto {
    @NotBlank
    @Size(min = 1, max = 64)
    @Schema(description = "Название меню", example = "Обеденное меню")
    private String name;

    @NotBlank
    @Size(min = 1, max = 512)
    @Schema(description = "Описание меню", example = "Действует в будние дни с 12:00 до 16:00")
    private String description;

    @Schema(description = "Список ID блюд для включения", example = "[1, 2, 3]")
    private List<Long> dishesIds;

    @NotNull
    @Schema(description = "ID ресторана, которому принадлежит меню", example = "10")
    private Long restaurantId;
}