package com.francode.hotelBackend.persistence.repository;

import com.francode.hotelBackend.domain.entity.Cleaning;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface JpaCleaningRepository extends JpaRepository<Cleaning, Long>, JpaSpecificationExecutor<Cleaning> {
    Page<Cleaning> findByEmployeeId(Long employeeId, Pageable pageable);
}
