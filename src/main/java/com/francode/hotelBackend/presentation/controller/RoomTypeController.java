package com.francode.hotelBackend.presentation.controller;

import com.francode.hotelBackend.business.services.interfaces.RoomTypeService;
import com.francode.hotelBackend.presentation.dto.request.RoomTypeRequestDTO;
import com.francode.hotelBackend.presentation.dto.response.RoomTypeResponseDTO;
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
public class RoomTypeController {

    private final RoomTypeService roomTypeService;

    @Autowired
    public RoomTypeController(RoomTypeService roomTypeService) {
        this.roomTypeService = roomTypeService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/room-type")
    public ResponseEntity<RoomTypeResponseDTO> create(@Valid @RequestBody RoomTypeRequestDTO roomTypeRequestDTO) {
        RoomTypeResponseDTO roomTypeResponseDTO = roomTypeService.create(roomTypeRequestDTO);
        return ResponseEntity.status(201).body(roomTypeResponseDTO);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/room-type/{id}")
    public ResponseEntity<RoomTypeResponseDTO> findById(@PathVariable Long id) {
        Optional<RoomTypeResponseDTO> roomTypeResponseDTO = roomTypeService.findById(id);
        return roomTypeResponseDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/room-type/{id}")
    public ResponseEntity<RoomTypeResponseDTO> update(@Valid @PathVariable Long id, @Valid @RequestBody RoomTypeRequestDTO roomTypeRequestDTO) {
        RoomTypeResponseDTO roomTypeResponseDTO = roomTypeService.update(id, roomTypeRequestDTO);
        return roomTypeResponseDTO != null ? ResponseEntity.ok(roomTypeResponseDTO) : ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/room-type/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        roomTypeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/room-types")
    public ResponseEntity<Page<RoomTypeResponseDTO>> findAll(
            @RequestParam(required = false) String field,
            @RequestParam(required = false) String value,
            Pageable pageable) {

        Page<RoomTypeResponseDTO> roomTypeResponseDTOs = roomTypeService.findAll(field, value, pageable);
        return ResponseEntity.ok(roomTypeResponseDTOs);
    }
}