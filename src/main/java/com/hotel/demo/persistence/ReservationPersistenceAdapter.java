package com.hotel.demo.persistence;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import com.hotel.demo.dto.Reservation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;

@Repository
public class ReservationPersistenceAdapter implements ReservationPersistencePort {

    private static final Logger logger = LoggerFactory.getLogger(ReservationPersistenceAdapter.class);
    @Value("${folder}")
    String folder;
    @Override
    public void saveReservation(Reservation reservation) throws Exception {
        logger.info("Start persistence reservation creation");
        write(reservation);
        logger.info("End persistence reservation creation");
    }

    @Override
    public void updateReservation(Reservation reservation) throws Exception {
        logger.info("Start persistence reservation update");
        final Reservation found = foundById(reservation.getId());
        if (found == null) {
            throw new Exception("Reservation not found");
        } else {
            update(reservation);
        }
        logger.info("End persistence reservation update");
    }

    @Override
    public Reservation getById(Integer reservationId) throws IOException {
        logger.info("Start persistence get reservation by id");
        Reservation reservation = foundById(reservationId);
        logger.info("End persistence get reservation by id");
        return reservation;
    }

    @Override
    public List<Reservation> getAllReservations() throws IOException {
        logger.info("Start persistence get reservations");
        final List<Reservation> reservationList = foundAll();
        logger.info("End persistence get reservations");
        return reservationList;
    }

    private void write(Reservation reservation) throws Exception {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        if (foundById(reservation.getId()) != null) {
            throw new Exception("Reservation already exist");
        } else {
            final List<Reservation> reservationEntityList = new ArrayList<>();
            final List<Reservation> dataFromFile = foundAll();
            reservationEntityList.addAll(dataFromFile);
            reservationEntityList.add(reservation);
            final File file = new File(
                    this.getClass().getClassLoader().getResource(folder).getFile()
            );
            objectMapper.writeValue(file, reservationEntityList);
        }
    }

    private void update(Reservation reservation) throws Exception {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        try {
            final List<Reservation> reservationEntityList = new ArrayList<>();
            final List<Reservation> dataFromFile = foundAll();
            reservationEntityList.addAll(dataFromFile);
            reservationEntityList.removeIf(d -> d.getId().equals(reservation.getId()));
            reservationEntityList.add(reservation);
            final File file = new File(
                    this.getClass().getClassLoader().getResource(folder).getFile()
            );
            objectMapper.writeValue(file, reservationEntityList);
        } catch (Exception e) {
         throw new Exception("Cannot update the reservation");
        }
    }

    private Reservation foundById(Integer reservationId) throws IOException {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
        final File file = new File(
                this.getClass().getClassLoader().getResource(folder).getFile()
        );
        Reservation reservation = null;
        if (file.length() != 0) {
            final Reservation[] reservationList = objectMapper.readValue(file,  Reservation[].class);
            for (Reservation entity: reservationList) {
                if (reservationId.equals(entity.getId())) {
                    reservation = entity;
                }
            }
        }
        return reservation;
    }
    private List<Reservation> foundAll() throws IOException {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        final File file = new File(
                this.getClass().getClassLoader().getResource(folder).getFile()
        );
        if (file.length() == 0) {
            return new ArrayList<>();
        } else {
            return Arrays.asList(objectMapper.readValue(file,  Reservation[].class));
        }
    }
}
