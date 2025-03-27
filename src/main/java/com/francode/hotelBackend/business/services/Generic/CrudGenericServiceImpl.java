package com.francode.hotelBackend.business.services.Generic;

import com.francode.hotelBackend.business.mapper.generic.Mapper;
import com.francode.hotelBackend.exceptions.custom.NoRecordsException;
import com.francode.hotelBackend.exceptions.custom.NotFoundException;
import com.francode.hotelBackend.exceptions.custom.ValidationException;
import com.francode.hotelBackend.persistence.generics.GenericRepository;
import jakarta.persistence.criteria.Path;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public abstract class CrudGenericServiceImpl<T, TRequestDTO, TResponseDTO, ID> implements CrudGenericService<T, TRequestDTO, TResponseDTO, ID> {

    protected GenericRepository<T, ID> repository;
    protected Mapper<TRequestDTO, TResponseDTO, T> mapper;

    public CrudGenericServiceImpl(GenericRepository<T, ID> repository, Mapper<TRequestDTO, TResponseDTO, T> mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Optional<TResponseDTO> findById(ID id) {
        if (id == null) {
            throw new ValidationException("El ID no puede ser nulo.");
        }

        Optional<T> entity = repository.findById(id);

        if (!entity.isPresent()) {
            throw new NotFoundException("No se encontró una entidad con el ID: " + id);
        }

        return entity.map(mapper::toResponseDTO);
    }

    @Override
    public TResponseDTO create(TRequestDTO requestDTO) {
        if (requestDTO == null) {
            throw new ValidationException("La solicitud no puede ser nula.");
        }

        T entity = mapper.toEntity(requestDTO);
        T savedEntity = repository.save(entity);
        return mapper.toResponseDTO(savedEntity);
    }

    @Override
    public TResponseDTO update(ID id, TRequestDTO requestDTO) {
        if (id == null) {
            throw new ValidationException("El ID no puede ser nulo.");
        }

        if (requestDTO == null) {
            throw new ValidationException("La solicitud no puede ser nula.");
        }

        Optional<T> entityOptional = repository.findById(id);

        if (!entityOptional.isPresent()) {
            throw new NotFoundException("No se encontró una entidad con el ID: " + id);
        }

        T existingEntity = entityOptional.get();
        mapper.updateEntityFromDTO(requestDTO, existingEntity);
        T savedEntity = repository.save(existingEntity);
        return mapper.toResponseDTO(savedEntity);
    }

    @Override
    public void delete(ID id) {
        if (id == null) {
            throw new ValidationException("El ID no puede ser nulo.");
        }

        Optional<T> entityOptional = repository.findById(id);

        if (!entityOptional.isPresent()) {
            throw new NotFoundException("No se encontró una entidad con el ID: " + id);
        }

        repository.deleteById(id);
    }

    @Override
    public Page<TResponseDTO> findAll(String field, String value, Pageable pageable) {
        if ((field != null && value == null) || (field == null && value != null)) {
            throw new ValidationException("Ambos, campo y valor, deben proporcionarse para la búsqueda.");
        }

        Specification<T> spec = Specification.where(null);

        if (field != null && value != null && !field.isEmpty() && !value.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) -> {
                Path<String> fieldPath = root.get(field);
                return criteriaBuilder.like(criteriaBuilder.lower(fieldPath), "%" + value.toLowerCase() + "%");
            });
        }

        Page<T> entities = repository.findAll(spec, pageable);

        if (entities.isEmpty()) {
            throw new NoRecordsException("Todavía no hay registros disponibles.");
        }

        return entities.map(mapper::toResponseDTO);
    }
}