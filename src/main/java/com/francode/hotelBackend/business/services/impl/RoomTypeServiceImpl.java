package com.francode.hotelBackend.business.services.impl;

import com.francode.hotelBackend.business.mapper.RoomTypeMapper;
import com.francode.hotelBackend.business.services.Generic.CrudGenericServiceImpl;
import com.francode.hotelBackend.business.services.interfaces.RoomTypeService;
import com.francode.hotelBackend.domain.entity.RoomType;
import com.francode.hotelBackend.persistence.repository.JpaRoomTypeRepository;
import com.francode.hotelBackend.presentation.dto.request.RoomTypeRequestDTO;
import com.francode.hotelBackend.presentation.dto.response.RoomTypeResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomTypeServiceImpl extends CrudGenericServiceImpl<RoomType, RoomTypeRequestDTO, RoomTypeResponseDTO, Long> implements RoomTypeService {

    private final JpaRoomTypeRepository roomTypeRepository;
    private final RoomTypeMapper roomTypeMapper;

    @Autowired
    public RoomTypeServiceImpl(JpaRoomTypeRepository roomTypeRepository, RoomTypeMapper roomTypeMapper) {
        super(roomTypeRepository, roomTypeMapper);
        this.roomTypeRepository = roomTypeRepository;
        this.roomTypeMapper = roomTypeMapper;
    }
}
