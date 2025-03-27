package com.francode.hotelBackend.business.services.impl;

import com.francode.hotelBackend.business.services.interfaces.ClientService;
import com.francode.hotelBackend.domain.entity.Client;
import com.francode.hotelBackend.exceptions.custom.NoRecordsException;
import com.francode.hotelBackend.exceptions.custom.NotFoundException;
import com.francode.hotelBackend.exceptions.custom.ValidationException;
import com.francode.hotelBackend.persistence.repository.JpaClientRepository;
import com.francode.hotelBackend.presentation.dto.request.ClientRequestDTO;
import com.francode.hotelBackend.presentation.dto.response.ClientResponseDTO;
import com.francode.hotelBackend.business.mapper.ClientMapper;
import com.francode.hotelBackend.exceptions.custom.CustomException;
import jakarta.persistence.criteria.Path;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    private final JpaClientRepository clientRepository;
    private final ClientMapper clientMapper;

    public ClientServiceImpl(JpaClientRepository clientRepository, ClientMapper clientMapper) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
    }

    @Override
    public Optional<ClientResponseDTO> findById(Long id) {
        if (id == null) {
            throw new ValidationException("El ID no puede ser nulo.");
        }

        Optional<Client> clientOptional = clientRepository.findById(id);
        if (!clientOptional.isPresent()) {
            throw new NotFoundException("No se encontró un cliente con el ID: " + id);
        }

        return clientOptional.map(clientMapper::toResponseDTO);
    }


    @Override
    public ClientResponseDTO create(ClientRequestDTO clientRequestDTO) {
        if (clientRequestDTO == null) {
            throw new ValidationException("La solicitud no puede ser nula.");
        }

        Client client = clientMapper.toEntity(clientRequestDTO);
        Client savedClient = clientRepository.save(client);
        return clientMapper.toResponseDTO(savedClient);
    }

    @Override
    public ClientResponseDTO update(Long id, ClientRequestDTO clientRequestDTO) {
        if (id == null) {
            throw new ValidationException("El ID no puede ser nulo.");
        }

        if (clientRequestDTO == null) {
            throw new ValidationException("La solicitud no puede ser nula.");
        }

        Optional<Client> clientOptional = clientRepository.findById(id);
        if (!clientOptional.isPresent()) {
            throw new NotFoundException("No se encontró un cliente con el ID: " + id);
        }

        Client existingClient = clientOptional.get();
        clientMapper.updateEntityFromDTO(clientRequestDTO, existingClient);
        Client updatedClient = clientRepository.save(existingClient);
        return clientMapper.toResponseDTO(updatedClient);
    }

    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new ValidationException("El ID no puede ser nulo.");
        }

        Optional<Client> clientOptional = clientRepository.findById(id);
        if (!clientOptional.isPresent()) {
            throw new NotFoundException("No se encontró un cliente con el ID: " + id);
        }

        clientRepository.deleteById(id);
    }

    @Override
    public Page<ClientResponseDTO> findAll(String field, String value, Pageable pageable) {
        if ((field != null && value == null) || (field == null && value != null)) {
            throw new ValidationException("Ambos, campo y valor, deben proporcionarse para la búsqueda.");
        }

        Specification<Client> spec = Specification.where(null);

        if (field != null && value != null && !field.isEmpty() && !value.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) -> {
                Path<String> fieldPath = root.get(field);
                return criteriaBuilder.like(criteriaBuilder.lower(fieldPath), "%" + value.toLowerCase() + "%");
            });
        }

        Page<Client> clients = clientRepository.findAll(spec, pageable);

        if (clients.isEmpty()) {
            throw new NoRecordsException("Todavía no hay registros disponibles.");
        }

        return clients.map(clientMapper::toResponseDTO);
    }

    @Override
    public Optional<ClientResponseDTO> findByDocumentNumber(String documentNumber) {
        if (documentNumber == null || documentNumber.isEmpty()) {
            throw new ValidationException("El número de documento no puede ser nulo o vacío.");
        }

        Optional<Client> clientOptional = clientRepository.findByDocumentNumber(documentNumber);

        if (!clientOptional.isPresent()) {
            throw new NotFoundException("No se encontró un cliente con el número de documento: " + documentNumber);
        }

        return clientOptional.map(clientMapper::toResponseDTO);
    }

}
