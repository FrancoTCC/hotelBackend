package com.francode.hotelBackend.business.services.interfaces;

import com.francode.hotelBackend.business.services.Generic.CrudGenericService;
import com.francode.hotelBackend.domain.entity.Reservation;
import com.francode.hotelBackend.presentation.dto.request.Reservation.ReservationRequestDTO;
import com.francode.hotelBackend.presentation.dto.response.ReservationResponseDTO;

public interface ReservationService  extends CrudGenericService<Reservation, ReservationRequestDTO, ReservationResponseDTO, Long> {

}
