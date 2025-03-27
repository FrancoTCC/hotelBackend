package com.francode.hotelBackend.business.mapper;

import com.francode.hotelBackend.business.mapper.generic.Mapper;
import com.francode.hotelBackend.domain.entity.Permission;
import com.francode.hotelBackend.presentation.dto.request.PermissionRequestDTO;
import com.francode.hotelBackend.presentation.dto.response.PermissionResponseDTO;
import org.mapstruct.MappingTarget;

@org.mapstruct.Mapper(componentModel = "spring")
public interface PermissionMapper extends Mapper<PermissionRequestDTO, PermissionResponseDTO, Permission> {

    @Override
    Permission toEntity(PermissionRequestDTO dto);

    @Override
    PermissionResponseDTO toResponseDTO(Permission entity);

    @Override
    void updateEntityFromDTO(PermissionRequestDTO dto, @MappingTarget Permission entity);
}
