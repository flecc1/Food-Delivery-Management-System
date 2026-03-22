package com.example.fooddelivery.dto.customer;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(description = "Данные для регистрации нового клиента")
public class CustomerCreateDto {
    @NotBlank
    @Size(min = 1, max = 64)
    @Schema(description = "Имя", example = "Иван")
    private String firstName;

    @NotBlank
    @Size(min = 1, max = 64)
    @Schema(description = "Фамилия", example = "Иванов")
    private String lastName;

    @Email
    @NotBlank
    @Schema(description = "Электронная почта", example = "ivan@example.com")
    private String email;

    @NotBlank
    @Size(min = 8, max = 128)
    @Schema(description = "Пароль (минимум 8 символов)", example = "password123")
    private String password;

    @NotBlank
    @Pattern(regexp = "^\\+?[0-9]{10,15}$")
    @Schema(description = "Номер телефона в международном формате", example = "+375291234567")
    private String phoneNumber;
}
