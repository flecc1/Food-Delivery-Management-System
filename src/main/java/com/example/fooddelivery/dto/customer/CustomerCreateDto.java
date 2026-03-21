package com.example.fooddelivery.dto.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerCreateDto {
    @NotBlank
    @Size(min = 1, max = 64, message = "must be not empty and from 1 to 64 symbols")
    private String firstName;

    @NotBlank
    @Size(min = 1, max = 64, message = "must be not empty and from 1 to 64 symbols")
    private String lastName;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 8, max = 128, message = "must be at least 8 symbols")
    private String password;

    @NotBlank
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "invalid phone format")
    private String phoneNumber;
}
