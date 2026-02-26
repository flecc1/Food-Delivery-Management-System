package com.example.fooddelivery.exception;

public class RestaurantNotFoundException extends ResourceNotFoundException {
    public RestaurantNotFoundException(String message) {
        super(message);
    }
}
