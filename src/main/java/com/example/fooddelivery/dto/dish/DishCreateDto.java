package com.example.fooddelivery.dto.dish;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DishCreateDto {
    @NotBlank
    @Size(min = 1, max = 64)
    private String name;

    @NotNull
    @Positive
    private double price;

    @NotBlank
    @Size(min = 1, max = 512)
    private String description;

    @Positive
    @NotNull
    private Long categoryId;

    @NotNull
    @Positive
    private Long menuId;
}
