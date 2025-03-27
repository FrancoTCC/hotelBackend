package com.francode.hotelBackend.business.mapper;

import com.francode.hotelBackend.domain.entity.Room;
import com.francode.hotelBackend.presentation.dto.request.RoomRequestDTO;
import com.francode.hotelBackend.presentation.dto.response.RoomResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RoomMapper {

    @Mapping(target = "roomType", ignore = true)
    @Mapping(target = "floor", ignore = true)
    @Mapping(target = "status", source = "status")
    Room toEntity(RoomRequestDTO dto);

    @Mapping(target = "roomTypeId", source = "roomType.id")
    @Mapping(target = "roomFloorNumber", source = "floor.number")
    @Mapping(target = "status", expression = "java(entity.getStatus().getDescripcion())")
    @Mapping(target = "statusCleaning", expression = "java(entity.getStatusCleaning().getDescripcion())")
    RoomResponseDTO toResponseDTO(Room entity);

    @Mapping(target = "roomType", ignore = true)
    @Mapping(target = "floor", ignore = true)
    @Mapping(target = "status", source = "status")
    void updateEntityFromDTO(RoomRequestDTO dto, @MappingTarget Room entity);
}