package com.francode.hotelBackend.persistence.repository;

import com.francode.hotelBackend.domain.entity.ProductRoom;
import com.francode.hotelBackend.persistence.generics.GenericRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaProductRoomRepository extends GenericRepository<ProductRoom, Long> {
}
