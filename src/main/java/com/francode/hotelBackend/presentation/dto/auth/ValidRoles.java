package com.francode.hotelBackend.presentation.dto.auth;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = RoleValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidRoles {
    String message() default "El rol es inválido.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
