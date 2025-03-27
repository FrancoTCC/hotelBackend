package com.francode.hotelBackend.presentation.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleRequestDTO {

    @NotEmpty(message = "El nombre no puede estar vacío")
    private String name;
}

