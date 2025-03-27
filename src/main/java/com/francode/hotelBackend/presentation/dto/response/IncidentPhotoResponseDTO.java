package com.francode.hotelBackend.presentation.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IncidentPhotoResponseDTO {
    private Long id;
    private Long incidentId;
    private String imageUrl;
}
