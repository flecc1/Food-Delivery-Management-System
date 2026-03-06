package com.example.fooddelivery.exception.massage;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorMassage {
    private String message;
    private int code;
    private LocalDateTime time;
}
