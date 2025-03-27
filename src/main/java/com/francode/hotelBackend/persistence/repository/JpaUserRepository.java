package com.francode.hotelBackend.persistence.repository;

import com.francode.hotelBackend.domain.entity.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaUserRepository extends JpaRepository<UserApp, Long> {
    boolean existsByEmail(String email);
    Optional<UserApp> findByEmail(String email);
}
