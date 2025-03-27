package com.francode.hotelBackend.business.mapper;

import com.francode.hotelBackend.business.mapper.generic.Mapper;
import com.francode.hotelBackend.domain.entity.ProductRoom;
import com.francode.hotelBackend.presentation.dto.request.ProductRoomRequestDTO;
import com.francode.hotelBackend.presentation.dto.response.ProductRoomResponseDTO;
import org.mapstruct.MappingTarget;

@org.mapstruct.Mapper(componentModel = "spring")
public interface ProductRoomMapper extends Mapper<ProductRoomRequestDTO, ProductRoomResponseDTO, ProductRoom> {

    @Override
    ProductRoom toEntity(ProductRoomRequestDTO dto);

    @Override
    ProductRoomResponseDTO toResponseDTO(ProductRoom entity);

    @Override
    void updateEntityFromDTO(ProductRoomRequestDTO dto, @MappingTarget ProductRoom entity);
}
