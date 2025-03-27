package com.francode.hotelBackend.presentation.dto.request.Reservation;

import jakarta.validation.Constraint;


@Constraint(validatedBy = EndDateAfterStartDateValidator.class)
public @interface EndDateAfterStartDate {
    String message() default "La fecha de salida debe ser posterior a la fecha de entrada";
    Class<?>[] groups() default {};
    Class<?>[] payload() default {};
}

