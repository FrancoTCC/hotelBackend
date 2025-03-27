package com.francode.hotelBackend.presentation.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomResponseDTO {

    private Long id;
    private String number;
    private String description;
    private Double dailyPrice;
    private Double hourlyPrice;
    private String status;
    private String statusCleaning;
    private Long roomTypeId;
    private Long roomFloorNumber;
}
