package com.dev.tickets.controller;

import com.dev.tickets.dto.ReservationResponse;
import com.dev.tickets.dto.purchase.CreateReservationRequest;
import com.dev.tickets.mapper.ReservationMapper;
import com.dev.tickets.model.ReservationEntity;
import com.dev.tickets.service.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<?> createReservation(@RequestBody CreateReservationRequest request){
        ReservationEntity reservation = reservationService.createReservation(request);
        ReservationResponse response = ReservationMapper.toDto(reservation);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getReservation(@PathVariable String id){
        ReservationEntity reservation = reservationService.getReservation(id);
        ReservationResponse response = ReservationMapper.toDto(reservation);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReservation(@PathVariable String id){
        reservationService.deleteReservation(id);
        return ResponseEntity.ok().build();
    }

}
