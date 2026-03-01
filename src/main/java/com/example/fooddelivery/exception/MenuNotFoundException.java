package com.example.fooddelivery.exception;

public class MenuNotFoundException extends ResourceNotFoundException {
    public MenuNotFoundException(String message) {
        super(message);
    }
}
