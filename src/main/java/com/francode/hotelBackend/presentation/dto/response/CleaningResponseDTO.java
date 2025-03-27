package com.francode.hotelBackend.presentation.dto.response;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class CleaningResponseDTO {
    private Long id;
    private Long employeeId;
    private Long roomId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime cancellationDate;
    private String status;
    private List<IncidentResponseDTO> incidents;
}
