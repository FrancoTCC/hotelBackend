package com.francode.hotelBackend.business.mapper;

import com.francode.hotelBackend.business.mapper.generic.Mapper;
import com.francode.hotelBackend.domain.entity.Category;
import com.francode.hotelBackend.presentation.dto.request.CategoryRequestDTO;
import com.francode.hotelBackend.presentation.dto.response.CategoryResponseDTO;
import org.mapstruct.MappingTarget;

@org.mapstruct.Mapper(componentModel = "spring")
public interface CategoryMapper extends Mapper<CategoryRequestDTO, CategoryResponseDTO, Category> {

    @Override
    Category toEntity(CategoryRequestDTO dto);

    @Override
    CategoryResponseDTO toResponseDTO(Category entity);

    @Override
    void updateEntityFromDTO(CategoryRequestDTO dto, @MappingTarget Category entity);
}
