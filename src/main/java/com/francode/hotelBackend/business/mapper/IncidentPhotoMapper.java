package com.francode.hotelBackend.business.mapper;

import com.francode.hotelBackend.domain.entity.IncidentPhoto;
import com.francode.hotelBackend.presentation.dto.request.IncidentPhotoRequestDTO;
import com.francode.hotelBackend.presentation.dto.response.IncidentPhotoResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring")
public interface IncidentPhotoMapper {
    @Mapping(target = "incident", ignore = true)
    @Mapping(target = "imageUrl", ignore = true)
    IncidentPhoto toEntity(IncidentPhotoRequestDTO dto);

    @Mapping(target = "incidentId", source = "incident.id")
    IncidentPhotoResponseDTO toResponseDTO(IncidentPhoto entity);

    @Mapping(target = "incident", ignore = true)
    @Mapping(target = "imageUrl", ignore = true)
    void updateEntityFromDTO(IncidentPhotoRequestDTO dto, @MappingTarget IncidentPhoto entity);
}
