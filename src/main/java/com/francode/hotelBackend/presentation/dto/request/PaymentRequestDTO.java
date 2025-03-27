package com.francode.hotelBackend.presentation.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class PaymentRequestDTO {

    @NotNull(message = "La ID de la reserva no puede ser nula")
    private Long reservationId;

    @Positive(message = "El monto debe ser positiva")
    @NotNull(message = "El monto no puede ser nulo")
    private BigDecimal amount;

    @NotNull(message = "El método de pago no puede ser nulo")
    private String paymentMethod;
}
