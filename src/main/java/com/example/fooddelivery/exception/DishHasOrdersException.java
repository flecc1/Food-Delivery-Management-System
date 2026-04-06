package com.example.fooddelivery.exception;

public class DishHasOrdersException extends RuntimeException {
    public DishHasOrdersException(String message) {
        super(message);
    }
}
