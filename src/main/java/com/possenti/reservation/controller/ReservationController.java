package com.possenti.reservation.controller;

import com.possenti.reservation.dto.ReservationCodeDto;
import com.possenti.reservation.dto.ReservationDto;
import com.possenti.reservation.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/reservations")
@Tag(name = "Reservation API", description = "Campsite Reservation API Contract")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @Operation(summary = "Provide information of the availability of the campsite for a given date range with the default being 1 month.",
            responses = {@ApiResponse(responseCode = "200", description = "Success")})
    @GetMapping("/available-dates")
    public ResponseEntity<List<LocalDate>> getAvailableDates(
            @RequestParam(value = "arrivalDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate arrivalDate,
            @RequestParam(value = "departureDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate departureDate) {
        final List<LocalDate> availableDays = reservationService.getAvailableDays(arrivalDate, departureDate);
        return ResponseEntity.ok(availableDays);
    }

    @Operation(summary = "Create new reservations", responses = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad request")})
    @PostMapping
    public ResponseEntity<ReservationCodeDto> create(
            @RequestBody @Valid ReservationDto reservationDto
    ) {
        final ReservationCodeDto reservationCodeDto = reservationService.save(reservationDto);
        return ResponseEntity.ok(reservationCodeDto);
    }

    @Operation(summary = "Update an existent reservation", responses = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "400", description = "Bad request")})
    @PutMapping("/{reservation_id}")
    public ResponseEntity<ReservationCodeDto> update(@PathVariable("reservation_id") String reservationId,
                                                     @RequestBody ReservationDto dto) {
        final ReservationCodeDto reservationCodeDto = reservationService.update(reservationId, dto);
        return ResponseEntity.ok(reservationCodeDto);
    }

    @Operation(summary = "Cancel an existent reservation", responses = {
            @ApiResponse(responseCode = "204", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not found")})
    @DeleteMapping("/{reservation_id}")
    public void cancel(@PathVariable("reservation_id") String reservationId) {
        reservationService.delete(reservationId);
        ResponseEntity.noContent().build();
    }
}
