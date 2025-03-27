package com.francode.hotelBackend.business.services.interfaces;

import com.francode.hotelBackend.business.services.Generic.CrudGenericService;
import com.francode.hotelBackend.domain.entity.FloorRooms;
import com.francode.hotelBackend.presentation.dto.request.FloorRoomsRequestDTO;
import com.francode.hotelBackend.presentation.dto.response.FloorRoomsResponseDTO;

public interface FloorRoomsService extends CrudGenericService<FloorRooms, FloorRoomsRequestDTO, FloorRoomsResponseDTO, Long> {
}
