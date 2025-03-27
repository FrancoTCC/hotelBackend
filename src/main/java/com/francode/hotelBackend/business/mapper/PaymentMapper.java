package com.francode.hotelBackend.business.mapper;

import com.francode.hotelBackend.business.mapper.generic.Mapper;
import com.francode.hotelBackend.domain.entity.Payment;
import com.francode.hotelBackend.presentation.dto.request.PaymentRequestDTO;
import com.francode.hotelBackend.presentation.dto.response.PaymentResponseDTO;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@org.mapstruct.Mapper(componentModel = "spring")
public interface PaymentMapper extends Mapper<PaymentRequestDTO, PaymentResponseDTO, Payment> {

    @Mapping(target = "reservation", ignore = true)
    Payment toEntity(PaymentRequestDTO dto);

    @Mapping(target = "reservationId", source = "reservation.id")
    PaymentResponseDTO toResponseDTO(Payment entity);

    @Mapping(target = "reservation", ignore = true)
    void updateEntityFromDTO(PaymentRequestDTO dto, @MappingTarget Payment entity);
}
