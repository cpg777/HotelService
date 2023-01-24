package com.hotel.demo.persistence;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotel.demo.persistence.entity.ReservationEntity;
import com.hotel.demo.service.dto.ReservationDto;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ReservationPersistenceAdapter implements ReservationPersistencePort {

    private static final Logger logger = LoggerFactory.getLogger(ReservationPersistenceAdapter.class);
    @Value("${folder}")
    String folder;
    @Override
    public void saveReservation(ReservationDto reservation) {
        logger.info("Start persistence reservation creation");
        final ReservationEntity reservationEntity = ReservationEntity.valueOf(reservation);
        write(reservationEntity);
        logger.info("End persistence reservation creation");
    }

    @Override
    public void updateReservation(ReservationDto reservation) throws Exception {
        logger.info("Start persistence reservation update");
        final ReservationEntity reservationEntity = ReservationEntity.valueOf(reservation);
        final ReservationEntity found = foundById(reservation.getReservationId());
        if (found == null) {
            throw new Exception("Reservation not found");
        } else {
            // remove it
            write(reservationEntity);
        }
        logger.info("End persistence reservation update");
    }

    @Override
    public ReservationDto getById(Integer reservationId) throws IOException {
        logger.info("Start persistence get reservation by id");
        foundById(reservationId);
        logger.info("End persistence get reservation by id");
        return null;
    }

    @Override
    public List<ReservationDto> getAllReservations() throws IOException {
        logger.info("Start persistence get reservations");
        final List<ReservationEntity> reservationList = foundAll();
        final List<ReservationDto> allReservation = new ArrayList<>();
        for (ReservationEntity entity: reservationList) {
            allReservation.add(ReservationDto.valueOf(entity));
        }
        return allReservation;
    }

    private void write(ReservationEntity reservation) {
        try (FileWriter file = new FileWriter(folder)) {
            file.write(reservation.toString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ReservationEntity foundById(Integer reservationId) throws IOException {
        final ObjectMapper objectMapper = new ObjectMapper();
        final List<ReservationEntity> reservationList = objectMapper.readValue(new File(folder), new TypeReference<List<ReservationEntity>>() {});
        Optional<ReservationEntity> reservation = null;
        for (ReservationEntity entity: reservationList) {
            if (reservationId.equals(entity.getId())) {
                reservation = Optional.of(entity);
            }
        }
        return reservation.orElse(null);
    }

    private List<ReservationEntity> foundAll() throws IOException {
        final ObjectMapper objectMapper = new ObjectMapper();
        final List<ReservationEntity> reservationList = objectMapper.readValue(new File(folder), new TypeReference<List<ReservationEntity>>() {});
        return reservationList;
    }
}
