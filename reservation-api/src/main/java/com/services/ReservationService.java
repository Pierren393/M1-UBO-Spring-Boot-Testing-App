package com.services;

import com.dtos.ReservationDto;

import java.util.List;



public interface ReservationService {

    ReservationDto saveReservation(ReservationDto reservationDto);

    ReservationDto getReservationById(Long reservationId);

    List<ReservationDto> getReservationsByUserId(Long userId);

    boolean deleteReservation(Long id);

    ReservationDto updateReservation(Long id, ReservationDto reservationDto);
}
