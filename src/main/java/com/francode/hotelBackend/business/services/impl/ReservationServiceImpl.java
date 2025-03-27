package com.francode.hotelBackend.business.services.impl;

import com.francode.hotelBackend.business.services.interfaces.ReservationService;
import com.francode.hotelBackend.business.mapper.ReservationMapper;
import com.francode.hotelBackend.domain.entity.Reservation;
import com.francode.hotelBackend.exceptions.custom.NoRecordsException;
import com.francode.hotelBackend.exceptions.custom.ValidationException;
import com.francode.hotelBackend.exceptions.custom.NotFoundException;
import com.francode.hotelBackend.persistence.repository.JpaReservationRepository;
import com.francode.hotelBackend.persistence.repository.JpaClientRepository;
import com.francode.hotelBackend.persistence.repository.JpaRoomRepository;
import com.francode.hotelBackend.presentation.dto.request.Reservation.ReservationRequestDTO;
import com.francode.hotelBackend.presentation.dto.response.ReservationResponseDTO;
import jakarta.persistence.criteria.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final JpaReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;
    private final JpaClientRepository clientRepository;
    private final JpaRoomRepository roomRepository;

    @Autowired
    public ReservationServiceImpl(JpaReservationRepository reservationRepository,
                                  ReservationMapper reservationMapper,
                                  JpaClientRepository clientRepository,
                                  JpaRoomRepository roomRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationMapper = reservationMapper;
        this.clientRepository = clientRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public Optional<ReservationResponseDTO> findById(Long id) {
        if (id == null) {
            throw new ValidationException("El ID de la reserva no puede ser nulo.");
        }
        return reservationRepository.findById(id)
                .map(reservationMapper::toResponseDTO)
                .or(() -> {
                    throw new NotFoundException("No se encontró una reserva con el ID: " + id);
                });
    }

    @Override
    public ReservationResponseDTO create(ReservationRequestDTO reservationRequestDTO) {
        if (reservationRequestDTO == null) {
            throw new ValidationException("La solicitud de creación de reserva no puede ser nula.");
        }

        var client = clientRepository.findById(reservationRequestDTO.getClientId())
                .orElseThrow(() -> new NotFoundException("No se encontró un cliente con el ID: " + reservationRequestDTO.getClientId()));
        var room = roomRepository.findById(reservationRequestDTO.getRoomId())
                .orElseThrow(() -> new NotFoundException("No se encontró una habitación con el ID: " + reservationRequestDTO.getRoomId()));

        Reservation reservation = reservationMapper.toEntity(reservationRequestDTO);
        reservation.setClient(client);
        reservation.setRoom(room);
        Reservation savedReservation = reservationRepository.save(reservation);

        return reservationMapper.toResponseDTO(savedReservation);
    }

    @Override
    public ReservationResponseDTO update(Long id, ReservationRequestDTO reservationRequestDTO) {
        if (id == null) {
            throw new ValidationException("El ID de la reserva no puede ser nulo.");
        }

        if (reservationRequestDTO == null) {
            throw new ValidationException("La solicitud de actualización de reserva no puede ser nula.");
        }

        Reservation existingReservation = reservationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No se encontró una reserva con el ID: " + id));

        var client = clientRepository.findById(reservationRequestDTO.getClientId())
                .orElseThrow(() -> new NotFoundException("No se encontró un cliente con el ID: " + reservationRequestDTO.getClientId()));
        var room = roomRepository.findById(reservationRequestDTO.getRoomId())
                .orElseThrow(() -> new NotFoundException("No se encontró una habitación con el ID: " + reservationRequestDTO.getRoomId()));

        reservationMapper.updateEntityFromDTO(reservationRequestDTO, existingReservation);
        existingReservation.setClient(client);
        existingReservation.setRoom(room);
        Reservation updatedReservation = reservationRepository.save(existingReservation);

        return reservationMapper.toResponseDTO(updatedReservation);
    }

    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new ValidationException("El ID de la reserva no puede ser nulo.");
        }

        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No se encontró una reserva con el ID: " + id));

        reservationRepository.delete(reservation);
    }

    @Override
    public Page<ReservationResponseDTO> findAll(String field, String value, Pageable pageable) {
        if ((field != null && value == null) || (field == null && value != null)) {
            throw new ValidationException("Ambos, campo y valor, deben proporcionarse para la búsqueda.");
        }

        Specification<Reservation> spec = Specification.where(null);

        if (field != null && value != null && !field.isEmpty() && !value.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) -> {
                Path<String> fieldPath = root.get(field);
                return criteriaBuilder.like(criteriaBuilder.lower(fieldPath), "%" + value.toLowerCase() + "%");
            });
        }

        Page<Reservation> reservations = reservationRepository.findAll(spec, pageable);

        if (reservations.isEmpty()) {
            throw new NoRecordsException("Todavía no hay registros disponibles.");
        }

        return reservations.map(reservationMapper::toResponseDTO);
    }
}