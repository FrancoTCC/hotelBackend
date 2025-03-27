package com.francode.hotelBackend.persistence.repository;

import com.francode.hotelBackend.domain.entity.RoomType;
import com.francode.hotelBackend.persistence.generics.GenericRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface JpaRoomTypeRepository extends GenericRepository<RoomType, Long> {
}
