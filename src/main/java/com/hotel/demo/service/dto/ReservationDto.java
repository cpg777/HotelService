package com.hotel.demo.service.dto;

import com.hotel.demo.controller.entity.Reservation;
import com.hotel.demo.persistence.entity.ReservationEntity;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@Getter
@NoArgsConstructor
@ToString
public class ReservationDto {
    private Integer reservationId;
    private String clientFullName;
    private Integer roomNumber;
    private List<LocalDate> reservationDates;

    public static ReservationDto valueOf(Reservation r) {
        return new ReservationDto(r.getReservationId(), r.getClientFullName(), r.getRoomNumber(), r.getReservationDates());
    }

    public static ReservationDto valueOf(ReservationEntity r) {
        return new ReservationDto(r.getId(), r.getClientFullName(), r.getRoomNumber(), r.getReservationDates());
    }
}
