package com.francode.hotelBackend.business.mapper.generic;

import org.mapstruct.MappingTarget;

public interface Mapper<TRequestDTO, TResponseDTO, T> {

    T toEntity(TRequestDTO dto);

    TResponseDTO toResponseDTO(T entity);

    void updateEntityFromDTO(TRequestDTO dto, @MappingTarget T entity);
}
