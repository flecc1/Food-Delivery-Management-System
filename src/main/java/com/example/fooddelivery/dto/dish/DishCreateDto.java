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
    @Size(min = 1, max = 64, message = "must be not empty and from 1 to 64 symbols")
    private String name;

    @NotNull
    @Positive(message = "must be not empty and greater than 0")
    private double price;

    @NotBlank
    @Size(min = 1, max = 512, message = "must be not empty and from 1 to 512 symbols")
    private String description;

    @Positive
    @NotNull(message = "must be not empty and greater than 0")
    private Long categoryId;

    @NotNull
    @Positive(message = "must be not empty and greater than 0")
    private Long menuId;
}
