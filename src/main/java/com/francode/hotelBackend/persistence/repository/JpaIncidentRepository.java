package com.francode.hotelBackend.persistence.repository;

import com.francode.hotelBackend.domain.entity.Incident;
import com.francode.hotelBackend.persistence.generics.GenericRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaIncidentRepository extends JpaRepository<Incident, Long>, JpaSpecificationExecutor<Incident> {
}
