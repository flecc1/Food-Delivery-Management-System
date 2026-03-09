package com.example.fooddelivery.controller;

import com.example.fooddelivery.exception.ResourceNotFoundException;
import com.example.fooddelivery.exception.RestaurantHasOrdersException;
import com.example.fooddelivery.exception.massage.ErrorMassage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RestaurantHasOrdersException.class)
    public ResponseEntity<ErrorMassage> handleRestaurantHasOrdersException(RestaurantHasOrdersException ex) {
        ErrorMassage details = new ErrorMassage(ex.getMessage(), HttpStatus.BAD_REQUEST.value(), LocalDateTime.now());
        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorMassage> handleRestaurantNotFoundException(ResourceNotFoundException ex) {
        ErrorMassage details = new ErrorMassage(ex.getMessage(), HttpStatus.NOT_FOUND.value(), LocalDateTime.now());
        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }
}
