package com.francode.hotelBackend.presentation.controller;

import com.francode.hotelBackend.business.services.interfaces.PaymentService;
import com.francode.hotelBackend.presentation.dto.request.PaymentRequestDTO;
import com.francode.hotelBackend.presentation.dto.response.PaymentResponseDTO;
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
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/payment")
    public ResponseEntity<PaymentResponseDTO> create(@Valid @RequestBody PaymentRequestDTO paymentRequestDTO) {
        PaymentResponseDTO paymentResponseDTO = paymentService.create(paymentRequestDTO);
        return ResponseEntity.status(201).body(paymentResponseDTO);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/payment/{id}")
    public ResponseEntity<PaymentResponseDTO> findById(@PathVariable Long id) {
        Optional<PaymentResponseDTO> paymentResponseDTO = paymentService.findById(id);
        return paymentResponseDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/payment/{id}")
    public ResponseEntity<PaymentResponseDTO> update(@Valid @PathVariable Long id, @RequestBody PaymentRequestDTO paymentRequestDTO) {
        PaymentResponseDTO paymentResponseDTO = paymentService.update(id, paymentRequestDTO);
        return paymentResponseDTO != null ? ResponseEntity.ok(paymentResponseDTO) : ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/payment/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        paymentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/payments")
    public ResponseEntity<Page<PaymentResponseDTO>> findAll(
            @RequestParam(required = false) String field,
            @RequestParam(required = false) String value,
            Pageable pageable) {

        Page<PaymentResponseDTO> paymentResponseDTOs = paymentService.findAll(field, value, pageable);
        return ResponseEntity.ok(paymentResponseDTOs);
    }
}
