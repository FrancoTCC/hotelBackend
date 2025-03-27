package com.francode.hotelBackend.business.services.interfaces;

import com.francode.hotelBackend.business.services.Generic.CrudGenericService;
import com.francode.hotelBackend.domain.entity.Cleaning;
import com.francode.hotelBackend.presentation.dto.request.cleaning.CleaningRequestDTO;
import com.francode.hotelBackend.presentation.dto.request.cleaning.CreateCleaningRequestDTO;
import com.francode.hotelBackend.presentation.dto.request.cleaning.UpdateCleaningStatusDTO;
import com.francode.hotelBackend.presentation.dto.response.CleaningResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CleaningService {
    Optional<CleaningResponseDTO> findById(Long id);

    CleaningResponseDTO create(CreateCleaningRequestDTO createCleaningRequestDTO);

    CleaningResponseDTO update(Long id, CleaningRequestDTO cleaningRequestDTO);

    void delete(Long id);

    Page<CleaningResponseDTO> findAll(String field, String value, Pageable pageable);

    CleaningResponseDTO updateCleaningStatus(Long cleaningId, UpdateCleaningStatusDTO updateCleaningStatus);

    Page<CleaningResponseDTO> getCleaningsByEmployeeId(Long employeeId, Pageable pageable);
}
