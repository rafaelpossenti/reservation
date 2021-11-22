package com.possenti.reservation.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Document
public class Reservation {

    @Id
    private String id;
    private String email;
    private String name;
    private LocalDate arrivalDate;
    private LocalDate departureDate;

    public Reservation(String email, String name, LocalDate arrivalDate, LocalDate departureDate) {
        this.email = email;
        this.name = name;
        this.arrivalDate = arrivalDate;
        this.departureDate = departureDate;
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

    //todo: name method
    public List<LocalDate> getBookingDates() {
        return this.arrivalDate.datesUntil(this.departureDate).collect(Collectors.toList());
    }

}
