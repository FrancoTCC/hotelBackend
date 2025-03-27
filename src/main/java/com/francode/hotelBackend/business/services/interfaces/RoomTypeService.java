package com.francode.hotelBackend.business.services.interfaces;

import com.francode.hotelBackend.business.services.Generic.CrudGenericService;
import com.francode.hotelBackend.domain.entity.RoomType;
import com.francode.hotelBackend.presentation.dto.request.RoomTypeRequestDTO;
import com.francode.hotelBackend.presentation.dto.response.RoomTypeResponseDTO;

public interface RoomTypeService extends CrudGenericService<RoomType, RoomTypeRequestDTO, RoomTypeResponseDTO, Long> {
}
