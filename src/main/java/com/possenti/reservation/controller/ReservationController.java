package com.possenti.reservation.controller;

import com.possenti.reservation.dto.RangeDateDto;
import com.possenti.reservation.dto.ReservationDto;
import com.possenti.reservation.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    //todo: inserir query-params star and end
    @GetMapping("/availability")
    public void getAvailability(@RequestBody RangeDateDto rangeDateDto) {
        reservationService.getAvailabilityDays(rangeDateDto);
    }

    @PostMapping
    public ResponseEntity<ReservationDto> reserve(@RequestBody @Valid ReservationDto reservationDto, BindingResult bindingResults) throws MethodArgumentNotValidException {
        reservationService.save(reservationDto, bindingResults);
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/{book_id}")
    public void cancel(@Valid @RequestBody ReservationDto reservationDto) {

    }

    @PutMapping("/{book_id}")
    public void update(@Valid @RequestBody ReservationDto reservationDto) {

    }
}
