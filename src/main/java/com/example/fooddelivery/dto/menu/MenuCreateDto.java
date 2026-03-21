package com.example.fooddelivery.dto.menu;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class MenuCreateDto {
    @NotBlank
    @Size(min = 1, max = 64, message = "must be not empty and from 1 to 64 symbols")
    private String name;

    @NotBlank
    @Size(min = 1, max = 512, message = "must be not empty from 1 to 512 symbols")
    private String description;

    private List<Long> dishesIds;

    @NotNull(message = "must be not empty")
    private Long restaurantId;
}