package com.possenti.reservation.dto;

import com.possenti.reservation.document.Reservation;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class ReservationDto {

    @NotNull(message = "email cant be null")
    @NotEmpty(message = "name cant be empty")
    @Email
    private String email;

    @NotNull(message = "name cant be null")
    @NotEmpty(message = "name cant be empty")
    private String name;

    private LocalDateTime arrivalDate;
    private LocalDateTime departureDate;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(LocalDateTime arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public LocalDateTime getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDateTime departureDate) {
        this.departureDate = departureDate;
    }

    public Reservation reservationDtoToReservation() {
        return new Reservation(
                this.email,
                this.name,
                this.arrivalDate,
                this.departureDate);
    }
}
