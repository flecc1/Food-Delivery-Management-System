package com.example.fooddelivery.exception;

public class RestaurantHasOrdersException extends RuntimeException {
    public RestaurantHasOrdersException(String message) {
        super(message);
    }
}
