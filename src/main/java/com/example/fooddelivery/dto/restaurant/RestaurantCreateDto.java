package com.example.fooddelivery.dto.restaurant;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RestaurantCreateDto {
    @NotBlank
    @Size(min = 2, max = 64, message = "must be from 2 to 64 symbols")
    String name;

    @NotBlank
    @Size(min = 2, max = 64, message = "must be not empty and from 2 to 64 symbols")
    String address;

    @NotBlank
    @Size(min = 2, max = 64, message = "must be not empty and from 2 to 64 symbols")
    String city;
}
