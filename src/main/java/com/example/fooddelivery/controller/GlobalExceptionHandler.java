package com.example.fooddelivery.controller;

import com.example.fooddelivery.exception.*;
import com.example.fooddelivery.exception.massage.ErrorMassage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RestaurantHasOrdersException.class)
    public ResponseEntity<ErrorMassage>  handleRestaurantHasOrdersException(RestaurantHasOrdersException ex) {
        ErrorMassage details = new ErrorMassage(ex.getMessage(), HttpStatus.BAD_REQUEST.value(), LocalDateTime.now());
        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RestaurantNotFoundException.class)
    public ResponseEntity<ErrorMassage> handleRestaurantNotFoundException(RestaurantNotFoundException ex) {
        ErrorMassage details = new ErrorMassage(ex.getMessage(), HttpStatus.NOT_FOUND.value(), LocalDateTime.now());
        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorMassage> handlerOrderNotFoundException(OrderNotFoundException ex) {
        ErrorMassage details = new ErrorMassage(ex.getMessage(), HttpStatus.NOT_FOUND.value(), LocalDateTime.now());
        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MenuNotFoundException.class)
    public ResponseEntity<ErrorMassage> handleMenuNotFoundException(MenuNotFoundException ex) {
        ErrorMassage details = new ErrorMassage(ex.getMessage(), HttpStatus.NOT_FOUND.value(), LocalDateTime.now());
        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DishNotFoundException.class)
    public ResponseEntity<ErrorMassage> handleDishNotFoundException(DishNotFoundException ex) {
        ErrorMassage details = new ErrorMassage(ex.getMessage(), HttpStatus.NOT_FOUND.value(), LocalDateTime.now());
        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ErrorMassage> handleCustomerNotFoundException(CustomerNotFoundException ex) {
        ErrorMassage details = new ErrorMassage(ex.getMessage(), HttpStatus.NOT_FOUND.value(), LocalDateTime.now());
        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ErrorMassage> handleCategoryNotFoundException(CategoryNotFoundException ex) {
        ErrorMassage details = new ErrorMassage(ex.getMessage(), HttpStatus.NOT_FOUND.value(), LocalDateTime.now());
        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }
}
