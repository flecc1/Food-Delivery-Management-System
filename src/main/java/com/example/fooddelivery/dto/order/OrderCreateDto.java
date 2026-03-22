package com.example.fooddelivery.dto.order;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.util.List;

@Data
@Schema(description = "Данные для оформления заказа")
public class OrderCreateDto {
    @NotEmpty
    @Schema(description = "Список ID выбранных блюд", example = "[10, 15, 22]")
    private List<Long> dishesId;

    @NotBlank
    @Size
    @Schema(description = "Адрес доставки", example = "г. Минск, ул. Ленина, д. 10, кв. 5")
    private String address;

    @NotNull
    @Positive
    @Schema(description = "ID клиента", example = "1")
    private Long customerId;

    @NotNull
    @Positive
    @Schema(description = "ID ресторана", example = "2")
    private Long restaurantId;
}