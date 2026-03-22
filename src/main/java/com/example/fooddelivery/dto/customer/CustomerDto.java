package com.example.fooddelivery.dto.customer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(description = "Объект передачи данных с информацией о клиенте")
public class CustomerDto {
    @Schema(description = "Уникальный идентификатор клиента", example = "105")
    private Long id;
    @Schema(description = "Имя клиента", example = "Александр")
    private String firstName;
    @Schema(description = "Фамилия клиента", example = "Петров")
    private String lastName;
    @Schema(description = "Электронная почта", example = "alex.petrov@example.com")
    private String email;
    @Schema(description = "Контактный номер телефона", example = "+375291112233")
    private String phoneNumber;
}
