package com.possenti.reservation.validation;

import com.possenti.reservation.dto.ReservationDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.Duration;

public class ReservationMaxStayValidation implements ConstraintValidator<ReservationMaxStay, ReservationDto> {

    @Override
    public boolean isValid(ReservationDto reservationDto, ConstraintValidatorContext context) {
        long stayDays = Duration.between(reservationDto.getArrivalDate().atStartOfDay(), reservationDto.getDepartureDate().atStartOfDay()).toDays();
        return stayDays <= 3;
    }
}
