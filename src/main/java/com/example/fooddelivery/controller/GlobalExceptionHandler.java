package com.example.fooddelivery.controller;

import com.example.fooddelivery.exception.*;
import com.example.fooddelivery.exception.message.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RestaurantHasOrdersException.class)
    public ResponseEntity<ErrorMessage> handleRestaurantHasOrdersException(RestaurantHasOrdersException ex) {
        log.error(ex.getMessage());
        ErrorMessage details = new ErrorMessage(ex.getMessage(), HttpStatus.BAD_REQUEST.value(), LocalDateTime.now());
        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleRestaurantNotFoundException(ResourceNotFoundException ex) {
        log.error(ex.getMessage());
        ErrorMessage details = new ErrorMessage(ex.getMessage(), HttpStatus.NOT_FOUND.value(), LocalDateTime.now());
        return new ResponseEntity<>(details, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OrderHasDishesException.class)
    public ResponseEntity<ErrorMessage> handleOrderHasDishesException(OrderHasDishesException ex) {
        log.error(ex.getMessage());
        ErrorMessage details = new ErrorMessage(ex.getMessage(), HttpStatus.BAD_REQUEST.value(), LocalDateTime.now());
        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MenuHasDishesException.class)
    public ResponseEntity<ErrorMessage> handleMenuHasDishesException(MenuHasDishesException ex) {
        log.error(ex.getMessage());
        ErrorMessage details = new ErrorMessage(ex.getMessage(), HttpStatus.BAD_REQUEST.value(), LocalDateTime.now());
        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CategoryHasDishesException.class)
    public ResponseEntity<ErrorMessage> handleCategoryHasDishesException(CategoryHasDishesException ex) {
        log.error(ex.getMessage());
        ErrorMessage details = new ErrorMessage(ex.getMessage(), HttpStatus.BAD_REQUEST.value(), LocalDateTime.now());
        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TransactionTestException.class)
    public ResponseEntity<ErrorMessage> handleTransactionTestException(TransactionTestException ex) {
        log.error(ex.getMessage());
        ErrorMessage details = new ErrorMessage(ex.getMessage(), HttpStatus.BAD_REQUEST.value(), LocalDateTime.now());
        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        String errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining("; "));

        log.error("Validation failed: {}", errors);
        ErrorMessage details = new ErrorMessage(errors,
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now());
        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleGlobalException(Exception ex) {
        log.error("Unexpected error occurred: {}", ex.getMessage(), ex);
        ErrorMessage details = new ErrorMessage(
                "internal server error: " + ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(details, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorMessage> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.error(ex.getMessage());
        ErrorMessage details = new ErrorMessage(ex.getMessage(), HttpStatus.BAD_REQUEST.value(), LocalDateTime.now());
        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorMessage> handleDishHasOrdersException(DishHasOrdersException ex) {
        log.error(ex.getMessage());
        ErrorMessage details = new ErrorMessage(ex.getMessage(), HttpStatus.BAD_REQUEST.value(), LocalDateTime.now());
        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }
}
