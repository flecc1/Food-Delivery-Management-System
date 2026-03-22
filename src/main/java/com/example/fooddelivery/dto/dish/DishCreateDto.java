package com.example.fooddelivery.dto.dish;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(description = "Данные для создания блюда")
public class DishCreateDto {
    @NotBlank
    @Size(min = 1, max = 64)
    @Schema(description = "Название блюда", example = "Борщ")
    private String name;

    @NotNull
    @Positive
    @Schema(description = "Цена блюда", example = "15.50")
    private double price;

    @NotBlank
    @Size
    @Schema(description = "Описание состава или веса", example = "Традиционный свекольный суп, 400г")
    private String description;

    @Positive
    @NotNull
    @Schema(description = "ID категории", example = "2")
    private Long categoryId;

    @NotNull
    @Positive
    @Schema(description = "ID меню, к которому относится блюдо", example = "1")
    private Long menuId;
}
