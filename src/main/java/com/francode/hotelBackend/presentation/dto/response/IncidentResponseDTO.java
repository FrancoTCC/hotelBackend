package com.francode.hotelBackend.presentation.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class IncidentResponseDTO {
    private Long id;
    private Long cleaningId;
    private String description;
    private List<IncidentPhotoResponseDTO> incidentPhotos;
}
