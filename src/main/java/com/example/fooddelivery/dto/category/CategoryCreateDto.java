package com.example.fooddelivery.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryCreateDto {
    @NotBlank
    @Size(min = 1, max = 64, message = "must be not empty from 1 to 64 symbols")
    private String name;
}
