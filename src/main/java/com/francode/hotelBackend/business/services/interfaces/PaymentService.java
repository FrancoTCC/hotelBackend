package com.francode.hotelBackend.business.services.interfaces;

import com.francode.hotelBackend.business.services.Generic.CrudGenericService;
import com.francode.hotelBackend.domain.entity.Payment;
import com.francode.hotelBackend.presentation.dto.request.PaymentRequestDTO;
import com.francode.hotelBackend.presentation.dto.response.PaymentResponseDTO;

public interface PaymentService extends CrudGenericService<Payment, PaymentRequestDTO, PaymentResponseDTO, Long> {
}
