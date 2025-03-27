package com.francode.hotelBackend.persistence.repository;

import com.francode.hotelBackend.domain.entity.Client;
import com.francode.hotelBackend.persistence.generics.GenericRepository;

import java.util.Optional;

public interface JpaClientRepository extends GenericRepository<Client, Long> {
    Optional<Client> findByDocumentNumber(String documentNumber);
}
