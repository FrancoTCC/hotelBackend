package com.francode.hotelBackend.presentation.dto.response.Reservation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationInfoDTO {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String status;
}
