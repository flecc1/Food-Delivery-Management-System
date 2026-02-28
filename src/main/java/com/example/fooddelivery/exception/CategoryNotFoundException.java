package com.example.fooddelivery.exception;

public class CategoryNotFoundException extends RestaurantNotFoundException {
    public CategoryNotFoundException(String message) {
        super(message);
    }
}
