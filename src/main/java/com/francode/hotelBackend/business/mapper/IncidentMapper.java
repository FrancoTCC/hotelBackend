package com.francode.hotelBackend.business.mapper;

import com.francode.hotelBackend.business.mapper.generic.Mapper;
import com.francode.hotelBackend.domain.entity.Incident;
import com.francode.hotelBackend.presentation.dto.request.IncidentRequestDTO;
import com.francode.hotelBackend.presentation.dto.response.IncidentResponseDTO;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@org.mapstruct.Mapper(componentModel = "spring")
public interface IncidentMapper {
    @Mapping(target = "cleaning", ignore = true)
    Incident toEntity(IncidentRequestDTO dto);

    @Mapping(target = "cleaningId", source = "cleaning.id")
    @Mapping(target = "incidentPhotos", source = "incidentPhotos")
    IncidentResponseDTO toResponseDTO(Incident entity);

    @Mapping(target = "cleaning", ignore = true)
    void updateEntityFromDTO(IncidentRequestDTO dto, @MappingTarget Incident entity);
}
