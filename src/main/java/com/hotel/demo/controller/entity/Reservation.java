package com.hotel.demo.controller.entity;

import com.hotel.demo.service.dto.ReservationDto;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@Getter
@ToString
public class Reservation {
    private Integer reservationId;
    private String clientFullName;
    private Integer roomNumber;
    private List<LocalDate> reservationDates;

    public static Reservation valueOf(ReservationDto r) {
        return new Reservation(r.getReservationId(), r.getClientFullName(), r.getRoomNumber(), r.getReservationDates());
    }
}
