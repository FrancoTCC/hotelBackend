package com.francode.hotelBackend.persistence.repository;

import com.francode.hotelBackend.domain.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface JpaEmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {
    Optional<Employee> findByDocumentNumber(String documentNumber);
    Optional<Employee> findByUserAppId(Long userId);
}
