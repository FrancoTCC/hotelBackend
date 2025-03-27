package com.francode.hotelBackend.presentation.controller;

import com.francode.hotelBackend.business.services.interfaces.IncidentPhotoService;
import com.francode.hotelBackend.presentation.dto.request.IncidentPhotoRequestDTO;
import com.francode.hotelBackend.presentation.dto.response.IncidentPhotoResponseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/v1/incident-photos")
@Validated
public class IncidentPhotoController {

    private final IncidentPhotoService incidentPhotoService;

    @Autowired
    public IncidentPhotoController(IncidentPhotoService incidentPhotoService) {
        this.incidentPhotoService = incidentPhotoService;
    }


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLEANER')")
    public ResponseEntity<IncidentPhotoResponseDTO> createIncidentPhoto(
            @Valid @ModelAttribute IncidentPhotoRequestDTO request) {
        IncidentPhotoResponseDTO response = incidentPhotoService.createIncidentPhoto(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLEANER')")
    public ResponseEntity<IncidentPhotoResponseDTO> getIncidentPhotoById(@PathVariable Long id) {
        return ResponseEntity.ok(incidentPhotoService.getIncidentPhotoById(id));
    }

    @PutMapping(path = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<IncidentPhotoResponseDTO> updateIncidentPhoto(
            @PathVariable Long id,
            @Valid @ModelAttribute IncidentPhotoRequestDTO request) {
        return ResponseEntity.ok(incidentPhotoService.updateIncidentPhoto(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteIncidentPhoto(@PathVariable Long id) {
        incidentPhotoService.deleteIncidentPhoto(id);
        return ResponseEntity.noContent().build();
    }
}