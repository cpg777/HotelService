package com.hotel.demo.persistence;

import com.hotel.demo.dto.Reservation;

import java.io.IOException;
import java.util.List;

public interface ReservationPersistencePort {
    void saveReservation(Reservation reservation) throws Exception;
    void updateReservation(Reservation reservation) throws Exception;

    Reservation getById(Integer reservationId) throws IOException;
    List<Reservation> getAllReservations() throws IOException;
}
