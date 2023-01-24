package com.hotel.demo.service;

import com.hotel.demo.controller.entity.Reservation;
import com.hotel.demo.persistence.ReservationPersistencePort;
import com.hotel.demo.service.dto.ReservationDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HotelService {
     private static final Logger logger = LoggerFactory.getLogger(HotelService.class);

     @Autowired
     private ReservationPersistencePort reservationPersistencePort;

     public void saveReservation(Reservation reservation) throws Exception {
         logger.info("Start processing the reservation creation");
         try {
             final ReservationDto reservationDto = ReservationDto.valueOf(reservation);
             reservationPersistencePort.saveReservation(reservationDto);
         } catch (Exception e) {
             throw new Exception("Unable to save the reservation", e);
         }
         logger.info("End processing the reservation creation");
     }

     public void updateReservation(Reservation reservation) throws Exception {
         logger.info("Start processing the reservation update");
         try {
             final ReservationDto reservationDto = ReservationDto.valueOf(reservation);
             reservationPersistencePort.updateReservation(reservationDto);
         } catch (Exception e) {
             throw new Exception("Unable to update the reservation", e);
         }
         logger.info("End processing the reservation creation");
     }

     public Reservation getReservationById(Integer reservationId) throws Exception {
         logger.info("Start processing the reservation with id: {}", reservationId);
         ReservationDto reservation;
         try {
             reservation = reservationPersistencePort.getById(reservationId);
         } catch (Exception e) {
             throw new Exception("Unable to find reservation", e);
         }
         logger.info("End processing the reservation with id: {}", reservationId);
         return Reservation.valueOf(reservation);
     }

     public List<Reservation> getReservations() throws Exception {
         logger.info("Start processing all the reservations");
         final List<ReservationDto> reservationDtoList = reservationPersistencePort.getAllReservations();
         if (reservationDtoList.size() == 0) {
             throw new Exception("No reservation data available");
         }
         final List<Reservation> foundReservations = new ArrayList<>();
         for (ReservationDto reservationDto: reservationDtoList) {
             foundReservations.add(Reservation.valueOf(reservationDto));
         }
         logger.info("End processing all the reservations");
         return foundReservations;
     }
}
