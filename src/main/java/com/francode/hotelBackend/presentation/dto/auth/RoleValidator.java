package com.francode.hotelBackend.presentation.dto.auth;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import com.francode.hotelBackend.domain.entity.ERole;

import java.util.Set;

public class RoleValidator implements ConstraintValidator<ValidRoles, Set<String>> {

    @Override
    public void initialize(ValidRoles constraintAnnotation) {
    }

    @Override
    public boolean isValid(Set<String> roles, ConstraintValidatorContext context) {
        // Verificar que cada rol esté en el conjunto de roles válidos (definidos en ERole)
        return roles.stream()
                .allMatch(role -> isValidRole(role));
    }

    // Método para verificar que el rol sea válido (pertenezca al enum ERole)
    private boolean isValidRole(String role) {
        try {
            ERole.valueOf(role);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}