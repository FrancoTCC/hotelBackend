package com.francode.hotelBackend.business.mapper;

import com.francode.hotelBackend.business.mapper.generic.Mapper;
import com.francode.hotelBackend.domain.entity.Reservation;
import com.francode.hotelBackend.presentation.dto.request.Reservation.ReservationRequestDTO;
import com.francode.hotelBackend.presentation.dto.response.Reservation.ReservationInfoDTO;
import com.francode.hotelBackend.presentation.dto.response.ReservationResponseDTO;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@org.mapstruct.Mapper(componentModel = "spring")
public interface ReservationMapper extends Mapper<ReservationRequestDTO, ReservationResponseDTO, Reservation> {

    @Mapping(target = "client", ignore = true)
    @Mapping(target = "room", ignore = true)
    Reservation toEntity(ReservationRequestDTO dto);

    @Mapping(target = "clientId", source = "client.id")
    @Mapping(target = "roomId", source = "room.id")
    @Mapping(target = "payments", source = "payments")
    ReservationResponseDTO toResponseDTO(Reservation entity);

    @Mapping(target = "client", ignore = true)
    @Mapping(target = "room", ignore = true)
    void updateEntityFromDTO(ReservationRequestDTO dto, @MappingTarget Reservation entity);

    List<ReservationInfoDTO> toReservationInfoDTOList(List<Reservation> reservations);
}
