package com.possenti.reservation.dto;

import com.possenti.reservation.document.Reservation;
import com.possenti.reservation.validation.ReservationArrivalDate;
import com.possenti.reservation.validation.ReservationArrivalDateBeforeDepartureDate;
import com.possenti.reservation.validation.ReservationMaxStay;

import javax.validation.constraints.Email;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@ReservationArrivalDate
@ReservationMaxStay
@ReservationArrivalDateBeforeDepartureDate
public class ReservationDto {

    private String id;

    @NotNull(message = "email cannot be null")
    @NotEmpty(message = "email cannot be empty")
    @Email
    private String email;

    @NotNull(message = "name cannot be null")
    @NotEmpty(message = "name cannot be empty")
    private String name;

    @NotNull(message = "arrivalDate cannot be null")
    @Future(message = "arrivalDate date must be a future date")
    private LocalDate arrivalDate;

    @NotNull(message = "departureDate cannot be null")
    @Future(message = "departure date must be a future date")
    private LocalDate departureDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
                this.id,
                this.email,
                this.name,
                this.arrivalDate,
                this.departureDate);
    }
}
