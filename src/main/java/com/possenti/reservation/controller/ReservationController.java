package com.possenti.reservation.controller;

import com.possenti.reservation.dto.ReservationDto;
import com.possenti.reservation.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController("/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @PostMapping
    public void reserve(@Valid @RequestBody ReservationDto reservationDto, BindingResult result) {
        reservationService.save(reservationDto, result);
    }

    @DeleteMapping("/{book_id}")
    public void cancel(@Valid @RequestBody ReservationDto reservationDto) {

    }

    @PutMapping("/{book_id}")
    public void update(@Valid @RequestBody ReservationDto reservationDto) {

    }
}
