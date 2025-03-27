package com.francode.hotelBackend.business.mapper;

import com.francode.hotelBackend.domain.entity.Employee;
import com.francode.hotelBackend.presentation.dto.request.EmployeeRequestDTO;
import com.francode.hotelBackend.presentation.dto.response.EmployeeResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    @Mapping(target = "userApp", ignore = true)
    @Mapping(target = "company", ignore = true)
    Employee toEntity(EmployeeRequestDTO dto);

    @Mapping(target = "userId", source = "userApp.id")
    @Mapping(target = "companyId", source = "company.id")
    EmployeeResponseDTO toResponseDTO(Employee entity);

    @Mapping(target = "userApp", ignore = true)
    @Mapping(target = "company", ignore = true)
    void updateEntityFromDTO(EmployeeRequestDTO dto, @MappingTarget Employee entity);
}