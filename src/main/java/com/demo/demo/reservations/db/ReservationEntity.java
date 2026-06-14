package com.demo.demo.reservations.db;

import com.demo.demo.reservations.domain.Reservation;
import com.demo.demo.reservations.domain.ReservationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@Table(name = "reservations")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ReservationEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id", nullable = false)
    private Long userId;
    @Column(name = "room_id", nullable = false)
    private Long roomId;
    @Column(name = "hotel_id", nullable = false)
    private Long hotelId;
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ReservationStatus status;


    public Reservation toDomain() {
        return new Reservation(
                this.id,
                this.userId,
                this.roomId,
                this.hotelId,
                this.startDate,
                this.endDate,
                this.status
        );
    }
}
