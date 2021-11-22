package com.possenti.reservation.validation;

import com.possenti.reservation.dto.ReservationDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class ReservationArrivalDateValidation implements ConstraintValidator<ReservationArrivalDate, ReservationDto> {

    @Override
    public boolean isValid(ReservationDto reservationDto, ConstraintValidatorContext context) {
        return LocalDate.now().isBefore(reservationDto.getArrivalDate()) &&
                reservationDto.getArrivalDate().isBefore(LocalDate.now().plusMonths(1).plusDays(1));

    }
}
