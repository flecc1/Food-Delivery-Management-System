package com.example.fooddelivery.dto.category;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(description = "Данные для создания или обновления категории блюд")
public class CategoryCreateDto {
    @NotBlank
    @Size(min = 1, max = 64)
    @Schema(description = "Название категории", example = "Пицца", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;
}
