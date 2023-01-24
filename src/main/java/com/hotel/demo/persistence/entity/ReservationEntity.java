package com.hotel.demo.persistence.entity;

import com.hotel.demo.service.dto.ReservationDto;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@NoArgsConstructor
@ToString
public class ReservationEntity {
    private Integer id;
    private String clientFullName;
    private Integer roomNumber;
    private List<LocalDate> reservationDates;

    public static ReservationEntity valueOf(ReservationDto r) {
        return new ReservationEntity(r.getReservationId(), r.getClientFullName(), r.getRoomNumber(), r.getReservationDates());
    }
}
