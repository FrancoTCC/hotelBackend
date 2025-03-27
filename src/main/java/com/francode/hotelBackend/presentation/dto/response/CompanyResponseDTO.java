package com.francode.hotelBackend.presentation.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyResponseDTO {

    private Long id;
    private String name;
    private String email;
    private String ruc;
    private String location;
    private String phone;
    private String logoUrl;
}
