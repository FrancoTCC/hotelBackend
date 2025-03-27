package com.francode.hotelBackend.business.services.interfaces;

import com.francode.hotelBackend.presentation.dto.request.IncidentPhotoRequestDTO;
import com.francode.hotelBackend.presentation.dto.response.IncidentPhotoResponseDTO;

public interface IncidentPhotoService {
    IncidentPhotoResponseDTO createIncidentPhoto(IncidentPhotoRequestDTO dto);
    IncidentPhotoResponseDTO updateIncidentPhoto(Long id, IncidentPhotoRequestDTO dto);
    void deleteIncidentPhoto(Long id);
    IncidentPhotoResponseDTO getIncidentPhotoById(Long id);
}