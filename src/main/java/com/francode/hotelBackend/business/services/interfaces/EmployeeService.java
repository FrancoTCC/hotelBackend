package com.francode.hotelBackend.business.services.interfaces;

import com.francode.hotelBackend.business.services.Generic.CrudGenericService;
import com.francode.hotelBackend.domain.entity.ERole;
import com.francode.hotelBackend.domain.entity.Employee;
import com.francode.hotelBackend.presentation.dto.request.EmployeeRequestDTO;
import com.francode.hotelBackend.presentation.dto.response.EmployeeResponseDTO;
import com.francode.hotelBackend.presentation.dto.response.EmployeeStatisticsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;


public interface EmployeeService extends CrudGenericService<Employee, EmployeeRequestDTO, EmployeeResponseDTO, Long> {
    Optional<EmployeeResponseDTO> findByDocumentNumber(String documentNumber);
    Optional<EmployeeResponseDTO> findByUserAppId(Long userId);
    Page<EmployeeResponseDTO> findByRole(ERole role, String field, String value, Pageable pageable);
    EmployeeStatisticsDTO findEmployeeStatistics(Long employeeId);
    Page<EmployeeStatisticsDTO> findTopEmployees(Pageable pageable);
}
