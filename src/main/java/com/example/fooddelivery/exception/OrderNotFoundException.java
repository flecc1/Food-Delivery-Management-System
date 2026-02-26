package com.example.fooddelivery.exception;

public class OrderNotFoundException extends ResourceNotFoundException {
    public OrderNotFoundException(String message) {
        super(message);
    }
}
