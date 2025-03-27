package com.francode.hotelBackend.persistence.repository;

import com.francode.hotelBackend.domain.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JpaReservationRepository extends JpaRepository<Reservation, Long>, JpaSpecificationExecutor<Reservation> {
    //Obtener una lista de reservas a futuro para una habitación
    @Query("SELECT res " +
            "FROM Reservation res WHERE res.room.id = :roomId AND res.startDate >= CURRENT_TIMESTAMP")
    List<Reservation> findFutureReservationsForRoom(Long roomId);
}
