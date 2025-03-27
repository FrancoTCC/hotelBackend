package com.francode.hotelBackend.presentation.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRoomResponseDTO {

    private Long id;
    private String name;
    private String description;
    private Double price;
    private Integer quantity;
}
