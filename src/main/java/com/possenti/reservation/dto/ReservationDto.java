package com.possenti.reservation.dto;

import com.possenti.reservation.document.Reservation;
import com.possenti.reservation.validation.ReservationArrivalDate;
import com.possenti.reservation.validation.ReservationArrivalDateBeforeDepartureDate;
import com.possenti.reservation.validation.ReservationMaxStay;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@ReservationMaxStay
@ReservationArrivalDate
@ReservationArrivalDateBeforeDepartureDate
public class ReservationDto {

    @NotNull(message = "email cannot be null")
    @NotEmpty(message = "email cannot be empty")
    @Email
    private String email;

    @NotNull(message = "name cannot be null")
    @NotEmpty(message = "name cannot be empty")
    private String name;

    @NotNull(message = "arrivalDate cannot be null")
    private LocalDate arrivalDate;

    @NotNull(message = "departureDate cannot be null")
    private LocalDate departureDate;

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

    public LocalDate getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(LocalDate arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDate departureDate) {
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
