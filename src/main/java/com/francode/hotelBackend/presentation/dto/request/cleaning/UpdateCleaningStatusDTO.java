package com.francode.hotelBackend.presentation.dto.request.cleaning;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCleaningStatusDTO {
    @NotNull(message = "El estado de limpieza no puede ser nulo")
    @Pattern(regexp = "^(EN_PROCESO|TERMINADO|CANCELADO)$", message = "El estado debe ser uno de los siguientes: EN_PROCESO, TERMINADO, CANCELADO")
    private String status;
}
