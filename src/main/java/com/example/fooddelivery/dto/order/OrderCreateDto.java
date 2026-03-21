package com.example.fooddelivery.dto.order;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.util.List;

@Data
public class OrderCreateDto {
    @NotEmpty
    private List<Long> dishesId;

    @NotBlank
    @Size
    private String address;

    @NotNull
    @Positive
    private Long customerId;

    @NotNull
    @Positive
    private Long restaurantId;
}