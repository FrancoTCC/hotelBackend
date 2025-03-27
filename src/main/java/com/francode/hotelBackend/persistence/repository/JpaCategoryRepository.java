package com.francode.hotelBackend.persistence.repository;

import com.francode.hotelBackend.domain.entity.Category;
import com.francode.hotelBackend.persistence.generics.GenericRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaCategoryRepository extends GenericRepository<Category, Long> {
}
