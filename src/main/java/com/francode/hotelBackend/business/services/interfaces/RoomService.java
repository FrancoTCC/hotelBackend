package com.francode.hotelBackend.business.services.interfaces;

import com.francode.hotelBackend.business.services.Generic.CrudGenericService;
import com.francode.hotelBackend.domain.entity.Room;
import com.francode.hotelBackend.presentation.dto.request.RoomRequestDTO;
import com.francode.hotelBackend.presentation.dto.response.Reservation.ReservationInfoDTO;
import com.francode.hotelBackend.presentation.dto.response.RoomResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface RoomService extends CrudGenericService<Room, RoomRequestDTO, RoomResponseDTO, Long> {
    // Obtener todas las habitaciones que no tengan reservas en un rango de fechas determinado
    Page<RoomResponseDTO> findAvailableRoomsForDates(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    // Obtener una lista de reservas con fecha de inicio, fecha de fin y estado para una habitación
    List<ReservationInfoDTO> findReservationsInfoForRoom(Long roomId);

    // Actualizar solo el estado de una habitación
    void updateRoomStatus(Long roomId, String status);

    Page<RoomResponseDTO> findRoomsWithCleaningStatus(Pageable pageable);
}
