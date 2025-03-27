package com.francode.hotelBackend.business.mapper;

import com.francode.hotelBackend.business.mapper.generic.Mapper;
import com.francode.hotelBackend.domain.entity.Company;
import com.francode.hotelBackend.presentation.dto.request.CompanyRequestDTO;
import com.francode.hotelBackend.presentation.dto.response.CompanyResponseDTO;
import org.mapstruct.MappingTarget;

@org.mapstruct.Mapper(componentModel = "spring")
public interface CompanyMapper extends Mapper<CompanyRequestDTO, CompanyResponseDTO, Company> {

    @Override
    Company toEntity(CompanyRequestDTO dto);

    @Override
    CompanyResponseDTO toResponseDTO(Company entity);

    @Override
    void updateEntityFromDTO(CompanyRequestDTO dto, @MappingTarget Company entity);
}
