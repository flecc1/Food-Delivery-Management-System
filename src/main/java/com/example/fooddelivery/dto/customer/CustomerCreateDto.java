package com.example.fooddelivery.dto.customer;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerCreateDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
}
