package com.possenti.reservation.validation;

import com.possenti.reservation.document.Reservation;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ReservationValidation implements Validator {

    @Override
    public void validate(Object target, Errors errors) {
        Reservation reservation = (Reservation) target;

        int daysRangeReservation = reservation.getDepartureDate().getDayOfYear() - reservation.getArrivalDate().getDayOfYear();
        if (daysRangeReservation >= 3) {
            errors.rejectValue("arrivalDate", "Match", "The campsite can be reserved for max 3 days.");
        }

    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Reservation.class.equals(clazz);
    }
}
