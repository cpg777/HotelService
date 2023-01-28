package com.hotel.demo.service;

import com.hotel.demo.dto.Reservation;
import com.hotel.demo.persistence.ReservationPersistencePort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelService {
    private static final Logger logger = LoggerFactory.getLogger(HotelService.class);

    @Autowired
    private ReservationPersistencePort reservationPersistencePort;

    public void saveReservation(Reservation reservation) throws Exception {
        logger.info("Start processing the reservation creation");
        try {
            reservationPersistencePort.saveReservation(reservation);
        } catch (Exception e) {
            throw new Exception("Unable to save the reservation", e);
        }
        logger.info("End processing the reservation creation");
    }

    public void updateReservation(Reservation reservation) throws Exception {
        logger.info("Start processing the reservation update");
        try {
            reservationPersistencePort.updateReservation(reservation);
        } catch (Exception e) {
            throw new Exception("Unable to update the reservation", e);
        }
        logger.info("End processing the reservation creation");
    }

    public Reservation getReservationById(Integer id) throws Exception {
        logger.info("Start processing the reservation with id: {}", id);
        try {
            Reservation reservation = reservationPersistencePort.getById(id);
            logger.info("End processing the reservation with id: {}", id);
            return reservation;
        } catch (Exception e) {
            throw new Exception("Unable to find reservation", e);
        }
    }

    public List<Reservation> getReservations() throws Exception {
        logger.info("Start processing all the reservations");
        final List<Reservation> reservationList = reservationPersistencePort.getAllReservations();
        if (reservationList.size() == 0) {
            throw new Exception("No reservation data available");
        }
        logger.info("End processing all the reservations");
        return reservationList;
    }
}
