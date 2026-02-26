package com.example.fooddelivery.exception;

public class DishNotFoundException extends ResourceNotFoundException {
    public DishNotFoundException(String message) {
        super(message);
    }
}
