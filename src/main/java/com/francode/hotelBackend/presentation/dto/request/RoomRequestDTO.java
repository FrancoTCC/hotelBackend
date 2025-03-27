package com.francode.hotelBackend.presentation.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomRequestDTO {

    @NotEmpty(message = "El número de habitación no puede estar vacío")
    @Size(max = 10, message = "El número de habitación no puede tener más de 10 caracteres")
    private String number;

    private String description;

    @NotNull(message = "El precio diario no puede ser nulo")
    @Min(value = 0, message = "El precio diario debe ser mayor o igual a 0")
    private Double dailyPrice;

    @NotNull(message = "El precio por hora no puede ser nulo")
    @Min(value = 0, message = "El precio por hora debe ser mayor o igual a 0")
    private Double hourlyPrice;

    @NotNull(message = "El estado de la habitación no puede ser nulo")
    @Pattern(regexp = "^(OCUPADO|DISPONIBLE|MANTENIMIENTO)$",
            message = "El estado de la habitación debe ser uno de los siguientes: OCUPADO, DISPONIBLE, MANTENIMIENTO")
    private String status;

    @NotNull(message = "El estado de limpieza no puede ser nulo")
    @Pattern(regexp = "^(LIMPIO|PARA_LIMPIAR|LIMPIANDO)$",
            message = "El estado de limpieza debe ser uno de los siguientes: LIMPIO, PARA_LIMPIAR, LIMPIANDO")
    private String statusCleaning;

    @JsonProperty("tipo_habitacion_id")
    @NotNull(message = "El ID del tipo de habitación no puede ser nulo")
    private Long roomTypeId;

    @JsonProperty("piso_id")
    @NotNull(message = "El ID del piso no puede ser nulo")
    private Long roomFloorId;
}
