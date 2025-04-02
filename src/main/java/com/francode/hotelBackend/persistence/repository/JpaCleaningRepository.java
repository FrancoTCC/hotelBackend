package com.francode.hotelBackend.persistence.repository;

import com.francode.hotelBackend.domain.entity.Cleaning;
import com.francode.hotelBackend.presentation.dto.response.CleaningDetailsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface JpaCleaningRepository extends JpaRepository<Cleaning, Long>, JpaSpecificationExecutor<Cleaning> {
    Page<Cleaning> findByEmployeeId(Long employeeId, Pageable pageable);

    @Query("SELECT new com.francode.hotelBackend.dto.CleaningDetailsDTO(" +
            "c.id, " +
            "c.startDate, " +
            "c.status, " +
            "c.employee.id, " +
            "c.employee.name) " +
            "FROM Cleaning c " +
            "WHERE c.room.id = :roomId AND c.status = 'EN_PROCESO'")
    Optional<CleaningDetailsDTO> findCleaningByRoomAndInProcessStatus(Long roomId);
}
