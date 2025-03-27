package com.francode.hotelBackend.business.mapper;

import com.francode.hotelBackend.domain.entity.Cleaning;
import com.francode.hotelBackend.presentation.dto.request.cleaning.CleaningRequestDTO;
import com.francode.hotelBackend.presentation.dto.request.cleaning.UpdateCleaningStatusDTO;
import com.francode.hotelBackend.presentation.dto.response.CleaningResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CleaningMapper {
    @Mapping(target = "employee", ignore = true)
    @Mapping(target = "room", ignore = true)
    Cleaning toEntity(CleaningRequestDTO dto);

    @Mapping(target = "employeeId", source = "employee.id")
    @Mapping(target = "roomId", source = "room.id")
    @Mapping(target = "incidents", source = "incidents")
    @Mapping(target = "status", expression = "java(entity.getStatus().getDescripcion())")
    CleaningResponseDTO toResponseDTO(Cleaning entity);

    @Mapping(target = "employee", ignore = true)
    @Mapping(target = "room", ignore = true)
    void updateEntityFromDTO(CleaningRequestDTO dto, @MappingTarget Cleaning entity);

    @Mapping(target = "status", source = "status")
    void updateStatusFromDTO(UpdateCleaningStatusDTO dto, @MappingTarget Cleaning entity);
}
