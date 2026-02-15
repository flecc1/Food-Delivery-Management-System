package com.example.fooddelivery.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Order {
    private Long id;
    private String status;
    private LocalDateTime createTime;
    private int amount;
}
