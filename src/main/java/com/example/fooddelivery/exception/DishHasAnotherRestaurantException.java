package com.example.fooddelivery.exception;

public class DishHasAnotherRestaurantException extends RuntimeException {
    public DishHasAnotherRestaurantException(String message) {
        super(message);
    }
}
