package com.francode.hotelBackend.presentation.dto.request.Reservation;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@EndDateAfterStartDate
public class ReservationRequestDTO {

    @NotNull(message = "La ID del cliente no puede ser nula")
    private Long clientId;

    @NotNull(message = "La ID de la habitación no puede ser nula")
    private Long roomId;

    @NotNull(message = "La fecha de inicio no puede ser nula")
    private LocalDateTime startDate;

    @NotNull(message = "La fecha de fin no puede ser nula")
    private LocalDateTime endDate;

    @NotNull(message = "El estado de la reserva no puede ser nulo")
    @Pattern(regexp = "^(PENDIENTE|CONFIRMADA|EN_EL_CHECK_IN|EN_EL_CHECK_OUT|CANCELADA|COMPLETADA|NO_SE_PRESENTO)$",
            message = "El estado debe ser uno de los siguientes: PENDIENTE, CONFIRMADA, EN_EL_CHECK_IN, EN_EL_CHECK_OUT, CANCELADA, COMPLETADA, NO_SE_PRESENTO")
    private String status;

    @NotNull(message = "El tipo de tarifa no puede ser nulo")
    @Pattern(regexp = "^(por hora|por día)$", message = "El tipo de tarifa debe ser 'por hora' o 'por día'")
    private String rateType; // Tipo de tarifa

    @NotNull(message = "El precio de la tarifa no puede ser nulo")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio de la tarifa debe ser mayor a 0")
    private BigDecimal priceRate;

    @NotNull(message = "El precio total no puede ser nulo")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio total debe ser mayor a 0")
    private BigDecimal totalPrice;

    @DecimalMin(value = "0.0", inclusive = false, message = "El precio adicional debe ser mayor a 0 si se proporciona")
    private BigDecimal additionalPrice;

    @Size(max = 255, message = "Los comentarios no pueden tener más de 255 caracteres")
    private String comments;
}