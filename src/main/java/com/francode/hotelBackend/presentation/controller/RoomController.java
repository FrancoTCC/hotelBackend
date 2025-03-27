package com.francode.hotelBackend.presentation.controller;

import com.francode.hotelBackend.business.services.interfaces.RoomService;
import com.francode.hotelBackend.presentation.dto.request.RoomRequestDTO;
import com.francode.hotelBackend.presentation.dto.response.Reservation.ReservationInfoDTO;
import com.francode.hotelBackend.presentation.dto.response.RoomResponseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class RoomController {

    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/room")
    public ResponseEntity<RoomResponseDTO> create(@Valid  @RequestBody RoomRequestDTO roomRequestDTO) {
        RoomResponseDTO roomResponseDTO = roomService.create(roomRequestDTO);
        return ResponseEntity.status(201).body(roomResponseDTO);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/room/{id}")
    public ResponseEntity<RoomResponseDTO> findById(@PathVariable Long id) {
        Optional<RoomResponseDTO> roomResponseDTO = roomService.findById(id);
        return roomResponseDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/room/{id}")
    public ResponseEntity<RoomResponseDTO> update(@Valid @PathVariable Long id, @RequestBody RoomRequestDTO roomRequestDTO) {
        RoomResponseDTO roomResponseDTO = roomService.update(id, roomRequestDTO);
        return roomResponseDTO != null ? ResponseEntity.ok(roomResponseDTO) : ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/room/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        roomService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/rooms")
    public ResponseEntity<Page<RoomResponseDTO>> findAll(
            @RequestParam(required = false) String field,
            @RequestParam(required = false) String value,
            Pageable pageable) {

        Page<RoomResponseDTO> roomResponseDTOs = roomService.findAll(field, value, pageable);
        return ResponseEntity.ok(roomResponseDTOs);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/rooms/available")
    public ResponseEntity<Page<RoomResponseDTO>> findAvailableRoomsForDates(
            @RequestParam("startDate") LocalDateTime startDate,
            @RequestParam("endDate") LocalDateTime endDate,
            Pageable pageable) {

        Page<RoomResponseDTO> availableRooms = roomService.findAvailableRoomsForDates(startDate, endDate, pageable);
        return ResponseEntity.ok(availableRooms);
    }

    // Obtener las reservas futuras de una habitación
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/room/{roomId}/reservations")
    public ResponseEntity<List<ReservationInfoDTO>> findReservationsInfoForRoom(@PathVariable Long roomId) {
        List<ReservationInfoDTO> reservationsInfo = roomService.findReservationsInfoForRoom(roomId);
        return ResponseEntity.ok(reservationsInfo);
    }

    // Actualizar el estado de una habitación
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/room/{roomId}/status")
    public ResponseEntity<Void> updateRoomStatus(@PathVariable Long roomId, @RequestParam String status) {
        roomService.updateRoomStatus(roomId, status);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLEANER')")
    @GetMapping("/rooms/cleaning-status")
    public ResponseEntity<Page<RoomResponseDTO>> findRoomsWithCleaningStatus(Pageable pageable) {
        Page<RoomResponseDTO> roomResponseDTOs = roomService.findRoomsWithCleaningStatus(pageable);
        return ResponseEntity.ok(roomResponseDTOs);
    }

}