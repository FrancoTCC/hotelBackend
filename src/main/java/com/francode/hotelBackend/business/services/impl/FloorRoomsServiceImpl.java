package com.francode.hotelBackend.business.services.impl;

import com.francode.hotelBackend.business.mapper.FloorRoomsMapper;
import com.francode.hotelBackend.business.services.Generic.CrudGenericServiceImpl;
import com.francode.hotelBackend.business.services.interfaces.FloorRoomsService;
import com.francode.hotelBackend.domain.entity.FloorRooms;
import com.francode.hotelBackend.persistence.repository.JpaFloorRoomsRepository;
import com.francode.hotelBackend.presentation.dto.request.FloorRoomsRequestDTO;
import com.francode.hotelBackend.presentation.dto.response.FloorRoomsResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FloorRoomsServiceImpl extends CrudGenericServiceImpl<FloorRooms, FloorRoomsRequestDTO, FloorRoomsResponseDTO, Long> implements FloorRoomsService {

    private final JpaFloorRoomsRepository floorRoomsRepository;
    private final FloorRoomsMapper floorRoomsMapper;

    @Autowired
    public FloorRoomsServiceImpl(JpaFloorRoomsRepository floorRoomsRepository, FloorRoomsMapper floorRoomsMapper) {
        super(floorRoomsRepository, floorRoomsMapper);
        this.floorRoomsRepository = floorRoomsRepository;
        this.floorRoomsMapper = floorRoomsMapper;
    }
}
