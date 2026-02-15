package com.example.fooddelivery.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Customer {
    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
}
