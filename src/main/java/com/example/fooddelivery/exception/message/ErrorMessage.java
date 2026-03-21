package com.example.fooddelivery.exception.message;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorMessage {
    private String message;
    private int code;
    private LocalDateTime time;
}
