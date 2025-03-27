package com.francode.hotelBackend.business.services.impl;

import com.francode.hotelBackend.business.services.interfaces.CloudinaryService;
import com.francode.hotelBackend.business.services.interfaces.IncidentPhotoService;
import com.francode.hotelBackend.domain.entity.Incident;
import com.francode.hotelBackend.domain.entity.IncidentPhoto;
import com.francode.hotelBackend.persistence.repository.CrudRepositoryIncidentPhoto;
import com.francode.hotelBackend.persistence.repository.JpaIncidentRepository;
import com.francode.hotelBackend.presentation.dto.request.IncidentPhotoRequestDTO;
import com.francode.hotelBackend.presentation.dto.response.IncidentPhotoResponseDTO;
import com.francode.hotelBackend.business.mapper.IncidentPhotoMapper;
import com.francode.hotelBackend.exceptions.custom.NotFoundException;
import com.francode.hotelBackend.exceptions.custom.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class IncidentPhotoServiceImpl implements IncidentPhotoService {

    private static final String CLOUDINARY_FOLDER = "incidencias/";

    private final CrudRepositoryIncidentPhoto photoRepository;
    private final CloudinaryService cloudinaryService;
    private final JpaIncidentRepository incidentRepository;
    private final IncidentPhotoMapper incidentPhotoMapper;

    @Autowired
    public IncidentPhotoServiceImpl(CrudRepositoryIncidentPhoto photoRepository,
                                    CloudinaryService cloudinaryService,
                                    JpaIncidentRepository incidentRepository,
                                    IncidentPhotoMapper incidentPhotoMapper) {
        this.photoRepository = photoRepository;
        this.cloudinaryService = cloudinaryService;
        this.incidentRepository = incidentRepository;
        this.incidentPhotoMapper = incidentPhotoMapper;
    }

    @Override
    public IncidentPhotoResponseDTO createIncidentPhoto(IncidentPhotoRequestDTO dto) {
        validateRequest(dto);

        Incident incident = getIncidentById(dto.getIncidentId());
        String imageUrl = uploadImage(dto.getImageUrl());

        IncidentPhoto photo = createPhotoEntity(dto, incident, imageUrl);
        return saveAndMapToResponse(photo);
    }

    @Override
    public IncidentPhotoResponseDTO updateIncidentPhoto(Long id, IncidentPhotoRequestDTO dto) {
        validateRequest(dto);

        IncidentPhoto existingPhoto = getPhotoById(id);
        Incident incident = getIncidentById(dto.getIncidentId());
        String imageUrl = uploadImage(dto.getImageUrl());

        updatePhotoEntity(existingPhoto, dto, incident, imageUrl);
        return saveAndMapToResponse(existingPhoto);
    }

    @Override
    public IncidentPhotoResponseDTO getIncidentPhotoById(Long id) {
        return incidentPhotoMapper.toResponseDTO(getPhotoById(id));
    }

    @Override
    public void deleteIncidentPhoto(Long id) {
        IncidentPhoto photo = getPhotoById(id);
        deleteImageFromCloudinary(photo.getImageUrl());
        photoRepository.delete(photo);
    }

    // Métodos auxiliares privados
    private void validateRequest(IncidentPhotoRequestDTO dto) {
        if (dto == null) {
            throw new ValidationException("La solicitud no puede ser nula");
        }
        if (dto.getIncidentId() == null) {
            throw new ValidationException("El ID de incidencia no puede ser nulo");
        }
        if (dto.getImageUrl() == null || dto.getImageUrl().isEmpty()) {
            throw new ValidationException("La imagen no puede ser nula");
        }
    }

    private Incident getIncidentById(Long id) {
        return incidentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Incidencia no encontrada con ID: " + id));
    }

    private IncidentPhoto getPhotoById(Long id) {
        return photoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Foto de incidencia no encontrada con ID: " + id));
    }

    private String uploadImage(MultipartFile imageFile) {
        try {
            return cloudinaryService.uploadImage(imageFile, CLOUDINARY_FOLDER);
        } catch (IOException e) {
            throw new ValidationException("Error al subir la imagen: " + e.getMessage());
        }
    }

    private void deleteImageFromCloudinary(String imageUrl) {
        try {
            cloudinaryService.deleteImage(imageUrl, CLOUDINARY_FOLDER);
        } catch (IOException e) {
            throw new ValidationException("Error al eliminar la imagen: " + e.getMessage());
        }
    }

    private IncidentPhoto createPhotoEntity(IncidentPhotoRequestDTO dto, Incident incident, String imageUrl) {
        IncidentPhoto photo = incidentPhotoMapper.toEntity(dto);
        photo.setIncident(incident);
        photo.setImageUrl(imageUrl);
        return photo;
    }

    private void updatePhotoEntity(IncidentPhoto photo, IncidentPhotoRequestDTO dto,
                                   Incident incident, String imageUrl) {
        incidentPhotoMapper.updateEntityFromDTO(dto, photo);
        photo.setIncident(incident);
        photo.setImageUrl(imageUrl);
    }

    private IncidentPhotoResponseDTO saveAndMapToResponse(IncidentPhoto photo) {
        IncidentPhoto savedPhoto = photoRepository.save(photo);
        return incidentPhotoMapper.toResponseDTO(savedPhoto);
    }
}