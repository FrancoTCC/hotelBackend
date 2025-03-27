package com.francode.hotelBackend.business.services.interfaces;

import com.francode.hotelBackend.business.services.Generic.CrudGenericService;
import com.francode.hotelBackend.domain.entity.Client;
import com.francode.hotelBackend.domain.entity.Employee;
import com.francode.hotelBackend.presentation.dto.request.ClientRequestDTO;
import com.francode.hotelBackend.presentation.dto.response.ClientResponseDTO;

import java.util.Optional;

public interface ClientService extends CrudGenericService<Employee, ClientRequestDTO, ClientResponseDTO, Long> {
    Optional<ClientResponseDTO> findByDocumentNumber(String documentNumber);
}
