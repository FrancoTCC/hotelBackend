package com.francode.hotelBackend.persistence.repository;

import com.francode.hotelBackend.domain.entity.EStatusCleaningRoom;
import com.francode.hotelBackend.domain.entity.Reservation;
import com.francode.hotelBackend.domain.entity.Room;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface JpaRoomRepository extends JpaRepository<Room, Long>, JpaSpecificationExecutor<Room> {

    // Consulta para obtener habitaciones disponibles en un rango de fechas con un filtro opcional por tipo de habitación
    @Query("SELECT r FROM Room r WHERE r.id NOT IN " +
            "(SELECT res.room.id FROM Reservation res WHERE " +
            "(res.startDate BETWEEN :startDate AND :endDate) OR " +
            "(res.endDate BETWEEN :startDate AND :endDate) OR " +
            "(res.startDate <= :startDate AND res.endDate >= :endDate) " +
            "AND (res.status != 'CANCELADA' AND res.status != 'NO_SE_PRESENTO')) " +
            "AND (:roomTypeId IS NULL OR r.roomType.id = :roomTypeId)")  // Filtro opcional por tipo de habitación
    Page<Room> findAvailableRoomsForDatesWithRoomType(
            LocalDateTime startDate,
            LocalDateTime endDate,
            @Param("roomTypeId") Long roomTypeId,  // Parámetro para tipo de habitación
            Pageable pageable);


    // Verificar si la habitación tiene reservas en un rango de fechas
    @Query("SELECT CASE WHEN COUNT(res) > 0 THEN true ELSE false END " +
            "FROM Reservation res WHERE res.room.id = :roomId AND " +
            "( (res.startDate BETWEEN :startDate AND :endDate) OR " +
            "  (res.endDate BETWEEN :startDate AND :endDate) OR " +
            "  (res.startDate <= :startDate AND res.endDate >= :endDate) ) " +
            "AND (res.status != 'CANCELADA' AND res.status != 'NO_SE_PRESENTO')")
    boolean hasReservationsInRange(Long roomId, LocalDateTime startDate, LocalDateTime endDate);

    // Obtener las reservas futuras asociadas a una habitación por su ID
    @Query("SELECT res FROM Reservation res WHERE res.room.id = :roomId AND res.startDate >= CURRENT_TIMESTAMP")
    List<Reservation> findReservationsByRoomIdWithFutureDates(Long roomId);

    // Actualizar solo el estado de una habitación
    @Modifying
    @Transactional
    @Query("UPDATE Room r SET r.status = :status WHERE r.id = :roomId")
    void updateRoomStatus(Long roomId, String status);

    @Query("SELECT r FROM Room r WHERE r.statusCleaning IN ('PARA_LIMPIAR', 'LIMPIANDO')")
    Page<Room> findRoomsWithCleaningStatus(Pageable pageable);

    @Modifying
    @Transactional
    @Query("UPDATE Room r SET r.statusCleaning = :statusCleaning WHERE r.id = :roomId")
    void updateRoomCleaningStatus(Long roomId, EStatusCleaningRoom statusCleaning);

    // Obtener todas las reservas futuras para todas las habitaciones
    @Query("SELECT res FROM Reservation res WHERE res.startDate >= CURRENT_TIMESTAMP")
    List<Reservation> findAllFutureReservations();

}
