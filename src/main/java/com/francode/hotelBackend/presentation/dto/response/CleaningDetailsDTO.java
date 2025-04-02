package com.francode.hotelBackend.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CleaningDetailsDTO {
    private Long cleaningId;
    private LocalDateTime startDate;
    private String status;
    private Long employeeId;
    private String employeeName;
}
