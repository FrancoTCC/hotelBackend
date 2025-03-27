package com.francode.hotelBackend.presentation.controller;

import com.francode.hotelBackend.business.services.interfaces.FloorRoomsService;
import com.francode.hotelBackend.presentation.dto.request.FloorRoomsRequestDTO;
import com.francode.hotelBackend.presentation.dto.response.FloorRoomsResponseDTO;
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
public class FloorRoomsController {

    private final FloorRoomsService floorRoomsService;

    @Autowired
    public FloorRoomsController(FloorRoomsService floorRoomsService) {
        this.floorRoomsService = floorRoomsService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/floor-room")
    public ResponseEntity<FloorRoomsResponseDTO> create(@Valid  @RequestBody FloorRoomsRequestDTO floorRoomsRequestDTO) {
        FloorRoomsResponseDTO floorRoomsResponseDTO = floorRoomsService.create(floorRoomsRequestDTO);
        return ResponseEntity.status(201).body(floorRoomsResponseDTO);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/floor-room/{id}")
    public ResponseEntity<FloorRoomsResponseDTO> findById(@PathVariable Long id) {
        Optional<FloorRoomsResponseDTO> floorRoomsResponseDTO = floorRoomsService.findById(id);
        return floorRoomsResponseDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/floor-room/{id}")
    public ResponseEntity<FloorRoomsResponseDTO> update(@Valid@PathVariable Long id, @RequestBody FloorRoomsRequestDTO floorRoomsRequestDTO) {
        FloorRoomsResponseDTO floorRoomsResponseDTO = floorRoomsService.update(id, floorRoomsRequestDTO);
        return floorRoomsResponseDTO != null ? ResponseEntity.ok(floorRoomsResponseDTO) : ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/floor-room/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        floorRoomsService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/floor-rooms")
    public ResponseEntity<Page<FloorRoomsResponseDTO>> findAll(
            @RequestParam(required = false) String field,
            @RequestParam(required = false) String value,
            Pageable pageable) {

        Page<FloorRoomsResponseDTO> floorRoomsResponseDTOs = floorRoomsService.findAll(field, value, pageable);
        return ResponseEntity.ok(floorRoomsResponseDTOs);
    }
}
