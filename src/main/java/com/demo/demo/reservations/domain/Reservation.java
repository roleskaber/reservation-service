package com.demo.demo.reservations.domain;

import com.demo.demo.reservations.db.ReservationEntity;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

import java.time.LocalDate;


public record Reservation(
        @Null
        Long id,
        @NotNull
        Long userId,
        @NotNull
        Long roomId,
        @NotNull
        Long hotelId,
        @FutureOrPresent
        @NotNull
        LocalDate startDate,
        @FutureOrPresent
        @NotNull
        LocalDate endDate,
        ReservationStatus status
) {
        public ReservationEntity toEntity() {
                return new ReservationEntity(
                        null,
                        this.userId,
                        this.roomId,
                        this.hotelId,
                        this.startDate,
                        this.endDate,
                        this.status
                );
        }
}
