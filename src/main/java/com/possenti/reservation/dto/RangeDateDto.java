package com.possenti.reservation.dto;

import java.time.LocalDate;

public class RangeDateDto {

    private LocalDate arrivalDate;
    private LocalDate departureDate;

    public RangeDateDto() {
    }

    public RangeDateDto(LocalDate arrivalDate, LocalDate departureDate) {
        this.arrivalDate = arrivalDate;
        this.departureDate = departureDate;
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
}
