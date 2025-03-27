package com.francode.hotelBackend.presentation.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignupRequest {
    @NotBlank(message = "El correo electrónico no puede estar vacío.")
    @Email(message = "El formato del correo electrónico es inválido.")
    private String email;

    @NotBlank(message = "La contraseña no puede estar vacía.")
    @Size(min = 6, max = 40, message = "La contraseña debe tener entre 6 y 40 caracteres.")
    private String password;

    @NotNull(message = "Los roles no pueden ser nulos.")
    @Size(min = 1, message = "Debe haber al menos un rol asignado.")
    @ValidRoles(message = "Uno o más roles no son válidos.")
    private Set<String> roles;
}
