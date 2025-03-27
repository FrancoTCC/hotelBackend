package com.francode.hotelBackend.business.services.impl;

import com.francode.hotelBackend.business.services.interfaces.CleaningService;
import com.francode.hotelBackend.business.mapper.CleaningMapper;
import com.francode.hotelBackend.business.services.interfaces.WebSocketService;
import com.francode.hotelBackend.domain.entity.Cleaning;
import com.francode.hotelBackend.domain.entity.CleaningStatus;
import com.francode.hotelBackend.domain.entity.EStatusCleaningRoom;
import com.francode.hotelBackend.exceptions.custom.NoRecordsException;
import com.francode.hotelBackend.exceptions.custom.ValidationException;
import com.francode.hotelBackend.exceptions.custom.NotFoundException;
import com.francode.hotelBackend.persistence.repository.JpaCleaningRepository;
import com.francode.hotelBackend.persistence.repository.JpaRoomRepository;
import com.francode.hotelBackend.persistence.repository.JpaEmployeeRepository;
import com.francode.hotelBackend.presentation.dto.request.cleaning.CleaningRequestDTO;
import com.francode.hotelBackend.presentation.dto.request.cleaning.CreateCleaningRequestDTO;
import com.francode.hotelBackend.presentation.dto.request.cleaning.UpdateCleaningStatusDTO;
import com.francode.hotelBackend.presentation.dto.response.CleaningResponseDTO;
import jakarta.persistence.criteria.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CleaningServiceImpl implements CleaningService {

    private final JpaCleaningRepository cleaningRepository;
    private final JpaRoomRepository roomRepository;
    private final JpaEmployeeRepository employeeRepository;
    private final CleaningMapper cleaningMapper;
    private final WebSocketService webSocketService;

    @Autowired
    public CleaningServiceImpl(JpaCleaningRepository cleaningRepository,
                               JpaRoomRepository roomRepository,
                               JpaEmployeeRepository employeeRepository,
                               CleaningMapper cleaningMapper,
                               WebSocketService webSocketService) {
        this.cleaningRepository = cleaningRepository;
        this.roomRepository = roomRepository;
        this.employeeRepository = employeeRepository;
        this.cleaningMapper = cleaningMapper;
        this.webSocketService = webSocketService;
    }

    @Override
    public Optional<CleaningResponseDTO> findById(Long id) {
        if (id == null) {
            throw new ValidationException("El ID de la limpieza no puede ser nulo.");
        }
        return cleaningRepository.findById(id)
                .map(cleaningMapper::toResponseDTO);
    }

    @Override
    public CleaningResponseDTO create(CreateCleaningRequestDTO createCleaningRequestDTO) {
        if (createCleaningRequestDTO == null) {
            throw new ValidationException("La solicitud de creación de limpieza no puede ser nula.");
        }

        // Obtener empleado
        var employee = employeeRepository.findById(createCleaningRequestDTO.getEmployeeId())
                .orElseThrow(() -> new NotFoundException("Empleado no encontrado con el ID: " + createCleaningRequestDTO.getEmployeeId()));

        // Obtener habitación
        var room = roomRepository.findById(createCleaningRequestDTO.getRoomId())
                .orElseThrow(() -> new NotFoundException("Habitación no encontrada con el ID: " + createCleaningRequestDTO.getRoomId()));

        // Validar el estado de la habitación
        if (room.getStatusCleaning() == EStatusCleaningRoom.LIMPIO) {
            throw new ValidationException("La habitación ya está limpia y no requiere limpieza.");
        } else if (room.getStatusCleaning() == EStatusCleaningRoom.LIMPIANDO) {
            throw new ValidationException("La habitación ya se está limpiando.");
        } else if (room.getStatusCleaning() == EStatusCleaningRoom.PARA_LIMPIAR) {
            // Si está en estado PARA_LIMPIAR, cambiar el estado de la habitación a LIMPIANDO
            room.setStatusCleaning(EStatusCleaningRoom.LIMPIANDO);
            // Enviar solo el cambio de `statusCleaning` a través de WebSocket
            webSocketService.sendRoomStatusUpdate(room.getId(), room.getStatusCleaning().name());
        }

        Cleaning cleaning = new Cleaning();
        cleaning.setEmployee(employee);
        cleaning.setRoom(room);
        cleaning.setStatus(CleaningStatus.EN_PROCESO);
        cleaning.setStartDate(LocalDateTime.now());

        Cleaning savedCleaning = cleaningRepository.save(cleaning);

        return cleaningMapper.toResponseDTO(savedCleaning);
    }

    @Override
    public CleaningResponseDTO update(Long id, CleaningRequestDTO cleaningRequestDTO) {
        if (id == null) {
            throw new ValidationException("El ID de la limpieza no puede ser nulo.");
        }

        var cleaning = cleaningRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Limpieza no encontrada con el ID: " + id));

        cleaningMapper.updateEntityFromDTO(cleaningRequestDTO, cleaning);

        Cleaning updatedCleaning = cleaningRepository.save(cleaning);
        return cleaningMapper.toResponseDTO(updatedCleaning);
    }

    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new ValidationException("El ID de la limpieza no puede ser nulo.");
        }

        Cleaning cleaning = cleaningRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Limpieza no encontrada con el ID: " + id));

        cleaningRepository.delete(cleaning);
    }

    @Override
    public Page<CleaningResponseDTO> findAll(String field, String value, Pageable pageable) {
        if ((field != null && value == null) || (field == null && value != null)) {
            throw new ValidationException("Ambos, campo y valor, deben proporcionarse para la búsqueda.");
        }

        Specification<Cleaning> spec = Specification.where(null);

        if (field != null && value != null && !field.isEmpty() && !value.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) -> {
                Path<String> fieldPath = root.get(field);
                return criteriaBuilder.like(criteriaBuilder.lower(fieldPath), "%" + value.toLowerCase() + "%");
            });
        }

        Page<Cleaning> cleanings = cleaningRepository.findAll(spec, pageable);

        if (cleanings.isEmpty()) {
            throw new NoRecordsException("No hay registros de limpiezas disponibles.");
        }

        return cleanings.map(cleaningMapper::toResponseDTO);
    }

    @Override
    public Page<CleaningResponseDTO> getCleaningsByEmployeeId(Long employeeId, Pageable pageable) {
        if (employeeId == null) {
            throw new ValidationException("El ID del empleado no puede ser nulo.");
        }

        Page<Cleaning> cleanings = cleaningRepository.findByEmployeeId(employeeId, pageable);
        return cleanings.map(cleaningMapper::toResponseDTO);
    }

    @Override
    public CleaningResponseDTO updateCleaningStatus(Long cleaningId, UpdateCleaningStatusDTO updateCleaningStatusDTO) {
        if (updateCleaningStatusDTO == null || updateCleaningStatusDTO.getStatus() == null) {
            throw new ValidationException("El estado de limpieza no puede ser nulo.");
        }

        if (!"EN_PROCESO".equals(updateCleaningStatusDTO.getStatus()) &&
                !"TERMINADO".equals(updateCleaningStatusDTO.getStatus()) &&
                !"CANCELADO".equals(updateCleaningStatusDTO.getStatus())) {
            throw new ValidationException("El estado debe ser uno de los siguientes: EN_PROCESO, TERMINADO, CANCELADO.");
        }

        Cleaning cleaning = cleaningRepository.findById(cleaningId)
                .orElseThrow(() -> new NotFoundException("No se encontró la limpieza con el ID: " + cleaningId));

        cleaningMapper.updateStatusFromDTO(updateCleaningStatusDTO, cleaning);

        // Cambiar las fechas y estados de la habitación
        if ("TERMINADO".equals(updateCleaningStatusDTO.getStatus())) {
            cleaning.setEndDate(LocalDateTime.now());
            cleaning.getRoom().setStatusCleaning(EStatusCleaningRoom.LIMPIO);
        } else if ("CANCELADO".equals(updateCleaningStatusDTO.getStatus())) {
            cleaning.setCancellationDate(LocalDateTime.now());
            cleaning.getRoom().setStatusCleaning(EStatusCleaningRoom.PARA_LIMPIAR);
        } else if ("EN_PROCESO".equals(updateCleaningStatusDTO.getStatus())) {
            cleaning.getRoom().setStatusCleaning(EStatusCleaningRoom.LIMPIANDO);
        }
        cleaningRepository.save(cleaning);
        // Enviar solo el cambio de `statusCleaning` de la habitación a través de WebSocket
        webSocketService.sendRoomStatusUpdate(cleaning.getRoom().getId(), cleaning.getRoom().getStatusCleaning().name());

        return cleaningMapper.toResponseDTO(cleaning);
    }
}