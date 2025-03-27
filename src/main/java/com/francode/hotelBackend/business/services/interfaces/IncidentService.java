package com.francode.hotelBackend.business.services.interfaces;

import com.francode.hotelBackend.business.services.Generic.CrudGenericService;
import com.francode.hotelBackend.domain.entity.Incident;
import com.francode.hotelBackend.presentation.dto.request.IncidentRequestDTO;
import com.francode.hotelBackend.presentation.dto.response.IncidentResponseDTO;

public interface IncidentService extends CrudGenericService<Incident, IncidentRequestDTO, IncidentResponseDTO, Long> {
}
