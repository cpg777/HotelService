package com.hotel.demo.controller;

import com.hotel.demo.controller.entity.Reservation;
import java.util.List;
public interface HotelController {
    void createReservation(Reservation reservation) throws Exception;
    List<Reservation> getAllReservations() throws Exception;
    void updateReservation(Reservation reservation) throws Exception;
    Reservation getReservationById(Integer id) throws Exception;
}
