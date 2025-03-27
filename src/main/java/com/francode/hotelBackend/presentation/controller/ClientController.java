package com.francode.hotelBackend.presentation.controller;

import com.francode.hotelBackend.business.services.interfaces.ClientService;
import com.francode.hotelBackend.presentation.dto.request.ClientRequestDTO;
import com.francode.hotelBackend.presentation.dto.response.ClientResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/client")
    public ResponseEntity<ClientResponseDTO> create(@RequestBody ClientRequestDTO clientRequestDTO) {
        ClientResponseDTO clientResponseDTO = clientService.create(clientRequestDTO);
        return ResponseEntity.status(201).body(clientResponseDTO);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/client/{id}")
    public ResponseEntity<ClientResponseDTO> findById(@PathVariable Long id) {
        Optional<ClientResponseDTO> clientResponseDTO = clientService.findById(id);
        return clientResponseDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/client/{id}")
    public ResponseEntity<ClientResponseDTO> update(@PathVariable Long id, @RequestBody ClientRequestDTO clientRequestDTO) {
        ClientResponseDTO clientResponseDTO = clientService.update(id, clientRequestDTO);
        return clientResponseDTO != null ? ResponseEntity.ok(clientResponseDTO) : ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/client/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        clientService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/clients")
    public ResponseEntity<Page<ClientResponseDTO>> findAll(
            @RequestParam(required = false) String field,
            @RequestParam(required = false) String value,
            Pageable pageable) {

        Page<ClientResponseDTO> clientResponseDTOs = clientService.findAll(field, value, pageable);
        return ResponseEntity.ok(clientResponseDTOs);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/client/document-number/{documentNumber}")
    public ResponseEntity<ClientResponseDTO> findByDocumentNumber(@PathVariable String documentNumber) {
        Optional<ClientResponseDTO> clientResponseDTO = clientService.findByDocumentNumber(documentNumber);
        return clientResponseDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}

