package com.hotel.demo.controller;

import com.hotel.demo.dto.Reservation;
import com.hotel.demo.service.HotelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
public class HotelAdapter implements HotelController {
    private static final Logger logger = LoggerFactory.getLogger(HotelAdapter.class);

    private static final String RESOURCE_NAME = "hotel";

    @Autowired
    private HotelService hotelService;

    private HotelAdapter(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @Override
    @PostMapping(path = "/" + RESOURCE_NAME, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.CREATED)
    public void createReservation(@RequestBody Reservation reservation) throws Exception {
        logger.info("Start request to create reservation");
        hotelService.saveReservation(reservation);
        logger.info("End request to create reservation");
    }

    @Override
    @GetMapping(path = "/" + RESOURCE_NAME, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Reservation> getAllReservations() throws Exception {
        logger.info("Start request to get all reservations");
        List<Reservation> reservationList = hotelService.getReservations();
        logger.info("End request to get all reservations");
        return reservationList;
    }

    @Override
    @PutMapping(path = "/" + RESOURCE_NAME, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.CREATED)
    public void updateReservation(@RequestBody Reservation reservation) throws Exception {
        logger.info("Start request to update reservation");
        hotelService.updateReservation(reservation);
        logger.info("End request to update reservation");
    }

    @Override
    @GetMapping(path = "/" + RESOURCE_NAME + "/id={id}")
    @ResponseBody
    public Reservation getReservationById(@PathVariable Integer id) throws Exception {
        logger.info("Start request to get reservation by id: {}", id);
        Reservation reservationFound = hotelService.getReservationById(id);
        logger.info("End request to get reservation by id: {}", id);
        return reservationFound;
    }
}
