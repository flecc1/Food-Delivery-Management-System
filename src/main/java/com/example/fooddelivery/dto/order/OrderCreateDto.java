package com.example.fooddelivery.dto.order;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class OrderCreateDto {
    @NotNull
    private List<Long> dishesId;

    @NotBlank
    @Size(min = 1, max = 64)
    private String address;

    @NotNull
    @Positive
    private Long customerId;

    @NotNull
    @Positive
    private Long restaurantId;
}