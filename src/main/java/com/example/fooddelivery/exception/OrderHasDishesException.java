package com.example.fooddelivery.exception;

public class OrderHasDishesException extends RuntimeException {
    public OrderHasDishesException(String message) {
        super(message);
    }
}
