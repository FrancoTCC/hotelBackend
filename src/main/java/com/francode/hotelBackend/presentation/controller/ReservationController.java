package com.francode.hotelBackend.presentation.controller;

import com.francode.hotelBackend.business.services.interfaces.ReservationService;
import com.francode.hotelBackend.presentation.dto.request.Reservation.ReservationRequestDTO;
import com.francode.hotelBackend.presentation.dto.response.ReservationResponseDTO;
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
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/reservation")
    public ResponseEntity<ReservationResponseDTO> create(@Valid @RequestBody ReservationRequestDTO reservationRequestDTO) {
        ReservationResponseDTO reservationResponseDTO = reservationService.create(reservationRequestDTO);
        return ResponseEntity.status(201).body(reservationResponseDTO);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/reservation/{id}")
    public ResponseEntity<ReservationResponseDTO> findById(@PathVariable Long id) {
        Optional<ReservationResponseDTO> reservationResponseDTO = reservationService.findById(id);
        return reservationResponseDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/reservation/{id}")
    public ResponseEntity<ReservationResponseDTO> update(@Valid @PathVariable Long id, @RequestBody ReservationRequestDTO reservationRequestDTO) {
        ReservationResponseDTO reservationResponseDTO = reservationService.update(id, reservationRequestDTO);
        return reservationResponseDTO != null ? ResponseEntity.ok(reservationResponseDTO) : ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/reservation/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reservationService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/reservations")
    public ResponseEntity<Page<ReservationResponseDTO>> findAll(
            @RequestParam(required = false) String field,
            @RequestParam(required = false) String value,
            Pageable pageable) {

        Page<ReservationResponseDTO> reservationResponseDTOs = reservationService.findAll(field, value, pageable);
        return ResponseEntity.ok(reservationResponseDTOs);
    }
}
