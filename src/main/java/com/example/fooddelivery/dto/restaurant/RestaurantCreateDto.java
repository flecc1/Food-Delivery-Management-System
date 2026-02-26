package com.example.fooddelivery.dto.restaurant;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RestaurantCreateDto {
    String name;
    String address;
    String city;
}
