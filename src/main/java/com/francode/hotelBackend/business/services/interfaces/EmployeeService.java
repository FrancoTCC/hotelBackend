package com.francode.hotelBackend.business.services.interfaces;

import com.francode.hotelBackend.business.services.Generic.CrudGenericService;
import com.francode.hotelBackend.domain.entity.Employee;
import com.francode.hotelBackend.presentation.dto.request.EmployeeRequestDTO;
import com.francode.hotelBackend.presentation.dto.response.EmployeeResponseDTO;

import java.util.Optional;


public interface EmployeeService extends CrudGenericService<Employee, EmployeeRequestDTO, EmployeeResponseDTO, Long> {
    Optional<EmployeeResponseDTO> findByDocumentNumber(String documentNumber);
    Optional<EmployeeResponseDTO> findByUserAppId(Long userId);
}
