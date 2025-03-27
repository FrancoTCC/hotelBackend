package com.francode.hotelBackend.exceptions.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ApiErrorDTO {
    private LocalDateTime timestamp;
    private int status;
    private String message;
    private String debugMessage;

    public ApiErrorDTO(int status, String message, String debugMessage) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.message = message;
        this.debugMessage = debugMessage;
    }
}