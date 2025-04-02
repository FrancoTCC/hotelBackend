package com.francode.hotelBackend.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeStatisticsDTO {
    private Long id;
    private String name;
    private Long completedCleanings;
    private Long canceledCleanings;
    private Double avgCleaningDuration;
}

