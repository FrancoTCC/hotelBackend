package com.francode.hotelBackend.business.services.Generic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CrudGenericService<T, TRequestDTO, TResponseDTO, ID> {

    Optional<TResponseDTO> findById(ID id);

    TResponseDTO create(TRequestDTO requestDTO);

    TResponseDTO update(ID id, TRequestDTO requestDTO);

    void delete(ID id);

    Page<TResponseDTO> findAll(String field, String value, Pageable pageable);

}
