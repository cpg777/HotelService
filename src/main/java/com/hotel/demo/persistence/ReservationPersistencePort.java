package com.hotel.demo.persistence;

import com.hotel.demo.service.dto.ReservationDto;

import java.io.IOException;
import java.util.List;

public interface ReservationPersistencePort {
    void saveReservation(ReservationDto reservation);
    void updateReservation(ReservationDto reservation) throws Exception;

    ReservationDto getById(Integer reservationId) throws IOException;
    List<ReservationDto> getAllReservations() throws IOException;
}
