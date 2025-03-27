package com.francode.hotelBackend.presentation.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyRequestDTO {

    @NotEmpty(message = "El nombre no puede estar vacío")
    private String name;

    @Email(message = "Formato de correo electrónico inválido")
    @NotEmpty(message = "El correo electrónico no puede estar vacío")
    private String email;

    @Pattern(regexp = "\\d{11}", message = "El RUC debe tener 11 dígitos")
    @NotEmpty(message = "El RUC no puede estar vacío")
    private String ruc;

    @NotEmpty(message = "La ubicación no puede estar vacía")
    private String location;

    @Pattern(regexp = "\\d{9,20}", message = "El teléfono debe tener entre 9 y 20 dígitos")
    @NotEmpty(message = "El teléfono no puede estar vacío")
    private String phone;

    @NotEmpty(message = "La URL del logo no puede estar vacía")
    private String logoUrl;
}

