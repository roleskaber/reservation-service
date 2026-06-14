package com.demo.demo.reservations.api;

import com.demo.demo.reservations.db.ReservationFilter;
import com.demo.demo.reservations.domain.Reservation;
import jakarta.validation.Valid;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/reservation")
public class ReservationController {

    private static final Logger logger = LoggerFactory.getLogger(ReservationController.class);
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/add")
    public ResponseEntity<Reservation> createReservation(
            @RequestBody @Valid Reservation reservation
    ) {
        var saved_reservation = reservationService.createReservation(reservation);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(saved_reservation);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Reservation>> getALlReservationById(
            @RequestParam(required = false) Long roomId,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) Integer pageNum
    ) {
        var filter = new ReservationFilter(
                roomId, userId, pageSize, pageNum
        );
        return ResponseEntity.ok(reservationService.getAllReservations(filter));
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<Reservation> updateReservation(
            @PathVariable Long id,
            @RequestBody Reservation reservation
    ) {
        return ResponseEntity.ok(reservationService.updateReservation(id, reservation));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Reservation> deleteReservationById(
            @PathVariable Long id
    ) {
        reservationService.deleteReservation(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(
            @PathVariable Long id
    ) {
        logger.info("GET /reservation/{}", id);
        return ResponseEntity.ok(reservationService.getReservationById(id));
    }

    @PostMapping("/{id}/approve")
    public ResponseEntity<Reservation> approveReservationById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(reservationService.approveReservationById(id));
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<Reservation> cancelReservationById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(reservationService.cancelReservationById(id));
    }

}
