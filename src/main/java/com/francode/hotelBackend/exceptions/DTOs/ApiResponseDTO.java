package com.francode.hotelBackend.exceptions.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ApiResponseDTO {
    private LocalDateTime timestamp;
    private int status;
    private String message;

    public ApiResponseDTO(int status, String message) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.message = message;
    }
}