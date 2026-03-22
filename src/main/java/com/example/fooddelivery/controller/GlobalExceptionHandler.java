package com.example.fooddelivery.controller;

import com.example.fooddelivery.exception.CategoryHasDishesException;
import com.example.fooddelivery.exception.MenuHasDishesException;
import com.example.fooddelivery.exception.OrderHasDishesException;
import com.example.fooddelivery.exception.ResourceNotFoundException;
import com.example.fooddelivery.exception.RestaurantHasOrdersException;
import com.example.fooddelivery.exception.TransactionTestException;
import com.example.fooddelivery.exception.message.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

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
        List<String> errorList = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .toList();

        log.error("Validation failed for request:");
        errorList.forEach(error -> log.error(" --> {}", error));

        ErrorMessage details = new ErrorMessage(
                String.join("; ", errorList),
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now());
        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }
}
