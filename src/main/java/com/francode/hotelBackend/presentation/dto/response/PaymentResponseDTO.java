package com.francode.hotelBackend.presentation.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class PaymentResponseDTO {

    private Long id;
    private Long reservationId;
    private BigDecimal amount;
    private String paymentMethod;
    private LocalDateTime paymentDate;
}
