package com.francode.hotelBackend.presentation.dto.request.Reservation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

class EndDateAfterStartDateValidator implements ConstraintValidator<EndDateAfterStartDate, ReservationRequestDTO> {

    @Override
    public boolean isValid(ReservationRequestDTO value, ConstraintValidatorContext context) {
        if (value.getStartDate() != null && value.getEndDate() != null) {
            return value.getEndDate().isAfter(value.getStartDate()); // La fecha de salida debe ser posterior a la de entrada
        }
        return true;
    }
}
