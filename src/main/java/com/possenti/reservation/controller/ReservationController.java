package com.possenti.reservation.controller;

import com.possenti.reservation.dto.ReservationDto;
import com.possenti.reservation.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping("/availability")
    public void getAvailability(@RequestHeader("arrivalDate") LocalDate arrivalDate,
                                @RequestHeader("departureDate") LocalDate departureDate) {
        reservationService.getAvailabilityDays(arrivalDate, departureDate);
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody @Valid ReservationDto reservationDto, BindingResult bindingResults) throws MethodArgumentNotValidException {
        final String reservationCode = reservationService.save(reservationDto, bindingResults);
        return ResponseEntity.ok(reservationCode);
    }

    @PatchMapping("/{reservation_id}")
    public ResponseEntity<String> update(@RequestBody ReservationDto dto,
                                         @PathVariable("reservation_id") String reservationId,
                                         BindingResult bindingResults) throws MethodArgumentNotValidException {
        final String reservationCode = reservationService.update(reservationId, dto, bindingResults);
        return ResponseEntity.ok(reservationCode);
    }

    @DeleteMapping("/{reservation_id}")
    public void cancel(@PathVariable("reservation_id") String reservationId) throws MethodArgumentNotValidException {
        reservationService.delete(reservationId);
    }
}
