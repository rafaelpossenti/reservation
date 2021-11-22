package com.possenti.reservation.validation;

import com.possenti.reservation.dto.ReservationDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ReservationArrivalDateBeforeDepartureDateValidation implements ConstraintValidator<ReservationArrivalDateBeforeDepartureDate, ReservationDto> {

    @Override
    public boolean isValid(ReservationDto reservationDto, ConstraintValidatorContext context) {
        return reservationDto.getArrivalDate().isBefore(reservationDto.getDepartureDate());
    }
}