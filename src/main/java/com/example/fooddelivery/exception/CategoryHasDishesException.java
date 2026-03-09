package com.example.fooddelivery.exception;

public class CategoryHasDishesException extends RuntimeException {
    public CategoryHasDishesException(String message) {
        super(message);
    }
}
