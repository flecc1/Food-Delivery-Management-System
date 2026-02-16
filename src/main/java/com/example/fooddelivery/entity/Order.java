package com.example.fooddelivery.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Order {
    private Long id;
    private String status;
    private LocalDateTime createTime;
    private int amount;
    private List<Dish> dishes;
}
