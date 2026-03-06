package com.mappers;

import com.dtos.ReservationDto;
import com.entities.Reservation;
import org.springframework.stereotype.Component;

@Component
public class ReservationMapper {

    public ReservationDto toDto(Reservation reservation) {
        if (reservation == null) {
            return null;
        }
        
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setId(reservation.getId());
        reservationDto.setStart(reservation.getStart());
        reservationDto.setEnd(reservation.getEnd());
        reservationDto.setFilmId(reservation.getFilmId());
        reservationDto.setPaymentId(reservation.getPaymentId());
        reservationDto.setUserId(reservation.getUserId());
        return reservationDto;
    }

    public Reservation toEntity(ReservationDto reservationDto) {
        if (reservationDto == null) {
            return null;
        }

        Reservation reservation = new Reservation();
        // On ne set l'ID que s'il existe (cas d'une mise à jour)
        if (reservationDto.getId() != null) {
            reservation.setId(reservationDto.getId());
        }
        reservation.setStart(reservationDto.getStart());
        reservation.setEnd(reservationDto.getEnd());
        reservation.setFilmId(reservationDto.getFilmId());
        reservation.setPaymentId(reservationDto.getPaymentId());
        reservation.setUserId(reservationDto.getUserId());
        return reservation;
    }
} 