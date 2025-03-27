package com.francode.hotelBackend.presentation.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeResponseDTO {

    private Long id;
    private String documentType;
    private String documentNumber;
    private String name;
    private  String lastName;
    private String phone;
    private String companyId;
    private String userId;
}
