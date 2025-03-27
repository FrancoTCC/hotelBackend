package com.francode.hotelBackend.business.services.impl;

import com.francode.hotelBackend.business.services.interfaces.IncidentService;
import com.francode.hotelBackend.business.mapper.IncidentMapper;
import com.francode.hotelBackend.domain.entity.Incident;
import com.francode.hotelBackend.exceptions.custom.NoRecordsException;
import com.francode.hotelBackend.exceptions.custom.ValidationException;
import com.francode.hotelBackend.exceptions.custom.NotFoundException;
import com.francode.hotelBackend.persistence.repository.JpaIncidentRepository;
import com.francode.hotelBackend.persistence.repository.JpaCleaningRepository;
import com.francode.hotelBackend.presentation.dto.request.IncidentRequestDTO;
import com.francode.hotelBackend.presentation.dto.response.IncidentResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import jakarta.persistence.criteria.Path;
import java.util.Optional;

@Service
public class IncidentServiceImpl implements IncidentService {

    private final JpaIncidentRepository incidentRepository;
    private final IncidentMapper incidentMapper;
    private final JpaCleaningRepository cleaningRepository;

    @Autowired
    public IncidentServiceImpl(JpaIncidentRepository incidentRepository,
                               IncidentMapper incidentMapper,
                               JpaCleaningRepository cleaningRepository) {
        this.incidentRepository = incidentRepository;
        this.incidentMapper = incidentMapper;
        this.cleaningRepository = cleaningRepository;
    }

    @Override
    public Optional<IncidentResponseDTO> findById(Long id) {
        if (id == null) {
            throw new ValidationException("El ID del incidente no puede ser nulo.");
        }
        return incidentRepository.findById(id)
                .map(incidentMapper::toResponseDTO)
                .or(() -> {
                    throw new NotFoundException("No se encontró un incidente con el ID: " + id);
                });
    }

    @Override
    public IncidentResponseDTO create(IncidentRequestDTO incidentRequestDTO) {
        if (incidentRequestDTO == null) {
            throw new ValidationException("La solicitud de creación de incidente no puede ser nula.");
        }

        var cleaning = cleaningRepository.findById(incidentRequestDTO.getCleaningId())
                .orElseThrow(() -> new NotFoundException("No se encontró una limpieza con el ID: " + incidentRequestDTO.getCleaningId()));

        Incident incident = incidentMapper.toEntity(incidentRequestDTO);
        incident.setCleaning(cleaning);
        Incident savedIncident = incidentRepository.save(incident);

        return incidentMapper.toResponseDTO(savedIncident);
    }

    @Override
    public IncidentResponseDTO update(Long id, IncidentRequestDTO incidentRequestDTO) {
        if (id == null) {
            throw new ValidationException("El ID del incidente no puede ser nulo.");
        }

        if (incidentRequestDTO == null) {
            throw new ValidationException("La solicitud de actualización de incidente no puede ser nula.");
        }

        Incident existingIncident = incidentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No se encontró un incidente con el ID: " + id));

        var cleaning = cleaningRepository.findById(incidentRequestDTO.getCleaningId())
                .orElseThrow(() -> new NotFoundException("No se encontró una limpieza con el ID: " + incidentRequestDTO.getCleaningId()));


        incidentMapper.updateEntityFromDTO(incidentRequestDTO, existingIncident);
        existingIncident.setCleaning(cleaning);
        Incident updatedIncident = incidentRepository.save(existingIncident);

        return incidentMapper.toResponseDTO(updatedIncident);
    }

    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new ValidationException("El ID del incidente no puede ser nulo.");
        }

        Incident incident = incidentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No se encontró un incidente con el ID: " + id));

        incidentRepository.delete(incident);
    }

    @Override
    public Page<IncidentResponseDTO> findAll(String field, String value, Pageable pageable) {
        if ((field != null && value == null) || (field == null && value != null)) {
            throw new ValidationException("Ambos, campo y valor, deben proporcionarse para la búsqueda.");
        }

        Specification<Incident> spec = Specification.where(null);

        if (field != null && value != null && !field.isEmpty() && !value.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) -> {
                Path<String> fieldPath = root.get(field);
                return criteriaBuilder.like(criteriaBuilder.lower(fieldPath), "%" + value.toLowerCase() + "%");
            });
        }

        Page<Incident> incidents = incidentRepository.findAll(spec, pageable);

        if (incidents.isEmpty()) {
            throw new NoRecordsException("No hay registros de incidentes disponibles.");
        }

        return incidents.map(incidentMapper::toResponseDTO);
    }
}