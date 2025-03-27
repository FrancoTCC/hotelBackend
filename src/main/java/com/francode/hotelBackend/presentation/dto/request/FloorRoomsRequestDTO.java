package com.francode.hotelBackend.presentation.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FloorRoomsRequestDTO {

    @NotNull(message = "El piso no puede ser nulo")
    @Min(value = 1, message = "El número de piso debe ser mayor o igual a 1")
    @Max(value = 100, message = "El número de piso no puede ser mayor que 100")
    private Integer number;
}


