package com.possenti.reservation.exception;

import java.time.LocalDate;

public class AnyReservationAvailableException extends RuntimeException {

    public AnyReservationAvailableException(LocalDate arrivalDate, LocalDate departureDate) {
        super(String.format("There is any reservation dates available from %s to %s",
                arrivalDate, departureDate));
    }

}
