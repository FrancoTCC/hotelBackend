package com.francode.hotelBackend.business.mapper;

import com.francode.hotelBackend.business.mapper.generic.Mapper;
import com.francode.hotelBackend.domain.entity.Role;
import com.francode.hotelBackend.presentation.dto.request.RoleRequestDTO;
import com.francode.hotelBackend.presentation.dto.response.RoleResponseDTO;
import org.mapstruct.MappingTarget;

@org.mapstruct.Mapper(componentModel = "spring")
public interface RoleMapper extends Mapper<RoleRequestDTO, RoleResponseDTO, Role> {

    @Override
    Role toEntity(RoleRequestDTO dto);

    @Override
    RoleResponseDTO toResponseDTO(Role entity);

    @Override
    void updateEntityFromDTO(RoleRequestDTO dto, @MappingTarget Role entity);
}
