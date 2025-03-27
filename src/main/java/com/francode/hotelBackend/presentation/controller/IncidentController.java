package com.francode.hotelBackend.presentation.controller;

import com.francode.hotelBackend.business.services.interfaces.IncidentService;
import com.francode.hotelBackend.presentation.dto.request.IncidentRequestDTO;
import com.francode.hotelBackend.presentation.dto.response.IncidentResponseDTO;
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
public class IncidentController {

    private final IncidentService incidentService;

    @Autowired
    public IncidentController(IncidentService incidentService) {
        this.incidentService = incidentService;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLEANER')")
    @GetMapping("/incident/{id}")
    public ResponseEntity<IncidentResponseDTO> findById(@PathVariable Long id) {
        Optional<IncidentResponseDTO> incidentResponseDTO = incidentService.findById(id);
        return incidentResponseDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLEANER')")
    @PostMapping("/incident")
    public ResponseEntity<IncidentResponseDTO> create(@Valid @RequestBody IncidentRequestDTO incidentRequestDTO) {
        IncidentResponseDTO incidentResponseDTO = incidentService.create(incidentRequestDTO);
        return ResponseEntity.status(201).body(incidentResponseDTO);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/incident/{id}")
    public ResponseEntity<IncidentResponseDTO> update(@Valid @PathVariable Long id, @RequestBody IncidentRequestDTO incidentRequestDTO) {
        IncidentResponseDTO incidentResponseDTO = incidentService.update(id, incidentRequestDTO);
        return incidentResponseDTO != null ? ResponseEntity.ok(incidentResponseDTO) : ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/incident/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        incidentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/incidents")
    public ResponseEntity<Page<IncidentResponseDTO>> findAll(
            @RequestParam(required = false) String field,
            @RequestParam(required = false) String value,
            Pageable pageable) {

        Page<IncidentResponseDTO> incidentResponseDTOs = incidentService.findAll(field, value, pageable);
        return ResponseEntity.ok(incidentResponseDTOs);
    }
}
