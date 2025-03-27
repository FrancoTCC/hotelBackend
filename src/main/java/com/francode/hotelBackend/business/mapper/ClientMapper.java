package com.francode.hotelBackend.business.mapper;

import com.francode.hotelBackend.business.mapper.generic.Mapper;
import com.francode.hotelBackend.domain.entity.Client;
import com.francode.hotelBackend.presentation.dto.request.ClientRequestDTO;
import com.francode.hotelBackend.presentation.dto.response.ClientResponseDTO;
import org.mapstruct.MappingTarget;

@org.mapstruct.Mapper(componentModel = "spring")
public interface ClientMapper extends Mapper<ClientRequestDTO, ClientResponseDTO, Client> {

    @Override
    Client toEntity(ClientRequestDTO dto);

    @Override
    ClientResponseDTO toResponseDTO(Client entity);

    @Override
    void updateEntityFromDTO(ClientRequestDTO dto, @MappingTarget Client entity);
}
