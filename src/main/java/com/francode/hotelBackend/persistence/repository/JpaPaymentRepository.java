package com.francode.hotelBackend.persistence.repository;

import com.francode.hotelBackend.domain.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface JpaPaymentRepository extends JpaRepository<Payment, Long>, JpaSpecificationExecutor<Payment> {
}
