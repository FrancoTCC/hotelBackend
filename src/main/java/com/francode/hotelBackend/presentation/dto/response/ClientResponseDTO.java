package com.francode.hotelBackend.presentation.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientResponseDTO {

    private Long id;
    private String clientType;
    private String documentType;
    private String documentNumber;
    private String name;
    private String phone;
}
