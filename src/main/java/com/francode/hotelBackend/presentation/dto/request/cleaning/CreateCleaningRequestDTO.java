package com.francode.hotelBackend.presentation.dto.request.cleaning;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCleaningRequestDTO {
    @NotNull(message = "El ID del empleado no puede ser nulo")
    private Long employeeId;

    @NotNull(message = "El ID de la habitación no puede ser nulo")
    private Long roomId;
}
