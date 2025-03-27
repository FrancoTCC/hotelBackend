package com.francode.hotelBackend.presentation.controller;

import com.francode.hotelBackend.business.services.interfaces.CleaningService;
import com.francode.hotelBackend.presentation.dto.request.cleaning.CleaningRequestDTO;
import com.francode.hotelBackend.presentation.dto.request.cleaning.CreateCleaningRequestDTO;
import com.francode.hotelBackend.presentation.dto.request.cleaning.UpdateCleaningStatusDTO;
import com.francode.hotelBackend.presentation.dto.response.CleaningResponseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/cleaning")
public class CleaningController {

    private final CleaningService cleaningService;

    @Autowired
    public CleaningController(CleaningService cleaningService) {
        this.cleaningService = cleaningService;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLEANER')")
    @GetMapping("/{id}")
    public ResponseEntity<CleaningResponseDTO> findById(@PathVariable Long id) {
        Optional<CleaningResponseDTO> cleaningResponseDTO = cleaningService.findById(id);
        return cleaningResponseDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLEANER')")
    @PostMapping
    public ResponseEntity<CleaningResponseDTO> create(@Valid @RequestBody CreateCleaningRequestDTO createCleaningRequestDTO) {
        CleaningResponseDTO cleaningResponseDTO = cleaningService.create(createCleaningRequestDTO);
        return ResponseEntity.status(201).body(cleaningResponseDTO);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<CleaningResponseDTO> update(@Valid @PathVariable Long id, @RequestBody CleaningRequestDTO cleaningRequestDTO) {
        CleaningResponseDTO cleaningResponseDTO = cleaningService.update(id, cleaningRequestDTO);
        return cleaningResponseDTO != null ? ResponseEntity.ok(cleaningResponseDTO) : ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        cleaningService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<Page<CleaningResponseDTO>> findAll(
            @RequestParam(required = false) String field,
            @RequestParam(required = false) String value,
            Pageable pageable) {

        Page<CleaningResponseDTO> cleaningResponseDTOs = cleaningService.findAll(field, value, pageable);
        return ResponseEntity.ok(cleaningResponseDTOs);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLEANER')")
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<Page<CleaningResponseDTO>> getCleaningsByEmployeeId(
            @PathVariable Long employeeId, Pageable pageable) {
        Page<CleaningResponseDTO> cleaningResponseDTOs = cleaningService.getCleaningsByEmployeeId(employeeId, pageable);
        return ResponseEntity.ok(cleaningResponseDTOs);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLEANER')")
    @PutMapping("/status/{cleaningId}")
    public ResponseEntity<CleaningResponseDTO> updateCleaningStatus(
            @PathVariable Long cleaningId, @Valid @RequestBody UpdateCleaningStatusDTO updateCleaningStatusDTO) {
        CleaningResponseDTO cleaningResponseDTO = cleaningService.updateCleaningStatus(cleaningId, updateCleaningStatusDTO);
        return ResponseEntity.ok(cleaningResponseDTO);
    }
}
