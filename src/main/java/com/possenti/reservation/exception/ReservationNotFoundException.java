package com.possenti.reservation.exception;

public class ReservationNotFoundException extends RuntimeException {

    public ReservationNotFoundException(String reservationId) {
        super(String.format("Account not found with id:  %s", reservationId));
    }

}
