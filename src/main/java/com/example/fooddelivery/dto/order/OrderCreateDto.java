package com.example.fooddelivery.dto.order;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.util.List;

@Data
public class OrderCreateDto {
    @NotEmpty(message = "must not be empty")
    private List<Long> dishesId;

    @NotBlank
    @Size(min = 1, max = 64, message = "must be from 1 to 64 symbols")
    private String address;

    @NotNull
    @Positive(message = "must be greater than 0")
    private Long customerId;

    @NotNull
    @Positive(message = "must be greater than 0")
    private Long restaurantId;
}