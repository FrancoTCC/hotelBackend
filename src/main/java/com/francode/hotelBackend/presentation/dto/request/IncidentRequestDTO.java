package com.francode.hotelBackend.presentation.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IncidentRequestDTO {
    @JsonProperty("limpieza_id")
    @NotNull(message = "El ID de limpieza no puede ser nulo")
    private Long cleaningId;

    @NotEmpty(message = "La descripción no puede ser nulo.")
    private String description;
}

