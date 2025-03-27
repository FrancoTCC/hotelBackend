package com.francode.hotelBackend.persistence.repository;

import com.francode.hotelBackend.domain.entity.Company;
import com.francode.hotelBackend.persistence.generics.GenericRepository;

public interface JpaCompanyRepository extends GenericRepository<Company, Long> {
}
