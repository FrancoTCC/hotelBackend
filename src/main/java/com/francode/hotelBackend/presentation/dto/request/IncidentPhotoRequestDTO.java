package com.francode.hotelBackend.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class IncidentPhotoRequestDTO {
    @NotNull(message = "El ID de incidencia no puede ser nulo")
    private Long incidentId;

    @NotNull(message = "La imagen no puede ser nulo")
    private MultipartFile imageUrl;
}
