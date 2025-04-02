package com.francode.hotelBackend.presentation.controller;

import com.francode.hotelBackend.business.services.interfaces.EmployeeService;
import com.francode.hotelBackend.domain.entity.ERole;
import com.francode.hotelBackend.presentation.dto.request.EmployeeRequestDTO;
import com.francode.hotelBackend.presentation.dto.response.EmployeeResponseDTO;
import com.francode.hotelBackend.presentation.dto.response.EmployeeStatisticsDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/employee")
    public ResponseEntity<EmployeeResponseDTO> create(@Valid @RequestBody EmployeeRequestDTO employeeRequestDTO) {
        EmployeeResponseDTO employeeResponseDTO = employeeService.create(employeeRequestDTO);
        return ResponseEntity.status(201).body(employeeResponseDTO);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLEANER')")
    @GetMapping("/employee/{id}")
    public ResponseEntity<EmployeeResponseDTO> findById(@PathVariable Long id) {
        Optional<EmployeeResponseDTO> employeeResponseDTO = employeeService.findById(id);
        return employeeResponseDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/employee/{id}")
    public ResponseEntity<EmployeeResponseDTO> update(@Valid @PathVariable Long id, @RequestBody EmployeeRequestDTO employeeRequestDTO) {
        EmployeeResponseDTO employeeResponseDTO = employeeService.update(id, employeeRequestDTO);
        return employeeResponseDTO != null ? ResponseEntity.ok(employeeResponseDTO) : ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/employee/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        employeeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/employees")
    public ResponseEntity<Page<EmployeeResponseDTO>> findAll(
            @RequestParam(required = false) String field,
            @RequestParam(required = false) String value,
            Pageable pageable) {

        Page<EmployeeResponseDTO> employeeResponseDTOs = employeeService.findAll(field, value, pageable);
        return ResponseEntity.ok(employeeResponseDTOs);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/employee/document-number/{documentNumber}")
    public ResponseEntity<EmployeeResponseDTO> findByDocumentNumber(@PathVariable String documentNumber) {
        Optional<EmployeeResponseDTO> employeeResponseDTO = employeeService.findByDocumentNumber(documentNumber);
        return employeeResponseDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/employee/user/{userId}")
    public ResponseEntity<EmployeeResponseDTO> findByUserAppId(@PathVariable Long userId) {
        Optional<EmployeeResponseDTO> employeeResponseDTO = employeeService.findByUserAppId(userId);
        return employeeResponseDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/employees/role")
    public ResponseEntity<Page<EmployeeResponseDTO>> findByRole(
            @RequestParam ERole role,
            @RequestParam(required = false) String field,
            @RequestParam(required = false) String value,
            Pageable pageable) {

        Page<EmployeeResponseDTO> employeeResponseDTOs = employeeService.findByRole(role, field, value, pageable);
        return ResponseEntity.ok(employeeResponseDTOs);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/statistics/{employeeId}")
    public ResponseEntity<EmployeeStatisticsDTO> findEmployeeStatistics(@PathVariable Long employeeId) {
        EmployeeStatisticsDTO employeeStatisticsDTO = employeeService.findEmployeeStatistics(employeeId);
        return ResponseEntity.ok(employeeStatisticsDTO);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/top")
    public ResponseEntity<Page<EmployeeStatisticsDTO>> findTopEmployees(
            @RequestParam String sortOrder,   // 'ASC' o 'DESC'
            Pageable pageable) {

        Page<EmployeeStatisticsDTO> employeeStatisticsDTOs = employeeService.findTopEmployees(sortOrder, pageable);
        return ResponseEntity.ok(employeeStatisticsDTOs);
    }
}
