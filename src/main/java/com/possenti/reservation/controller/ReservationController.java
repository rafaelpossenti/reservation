package com.possenti.reservation.controller;

import com.possenti.reservation.dto.ReservationCodeDto;
import com.possenti.reservation.dto.ReservationDto;
import com.possenti.reservation.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping("/available-dates")
    public ResponseEntity<List<LocalDate>> getAvailableDates(
            @RequestParam(value = "arrivalDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate arrivalDate,
            @RequestParam(value = "departureDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate departureDate) {
        final List<LocalDate> availableDays = reservationService.getAvailableDays(arrivalDate, departureDate);
        return ResponseEntity.ok(availableDays);
    }

    @PostMapping
    public ResponseEntity<ReservationCodeDto> create(
            @RequestBody @Valid ReservationDto reservationDto
    ) {
        final ReservationCodeDto reservationCodeDto = reservationService.save(reservationDto);
        return ResponseEntity.ok(reservationCodeDto);
    }

    @PatchMapping("/{reservation_id}")
    public ResponseEntity<ReservationCodeDto> update(@RequestBody ReservationDto dto,
                                                     @PathVariable("reservation_id") String reservationId) {
        final ReservationCodeDto reservationCodeDto = reservationService.update(reservationId, dto);
        return ResponseEntity.ok(reservationCodeDto);
    }

    @DeleteMapping("/{reservation_id}")
    public void cancel(@PathVariable("reservation_id") String reservationId) {
        reservationService.delete(reservationId);
    }
}
