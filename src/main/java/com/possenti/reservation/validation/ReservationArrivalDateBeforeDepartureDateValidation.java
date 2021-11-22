package com.possenti.reservation.validation;

import com.possenti.reservation.dto.ReservationDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ReservationArrivalDateBeforeDepartureDateValidation implements ConstraintValidator<ReservationArrivalDateBeforeDepartureDate, ReservationDto> {

    private Logger logger = LoggerFactory.getLogger(ReservationArrivalDateBeforeDepartureDateValidation.class);

    @Override
    public boolean isValid(ReservationDto reservationDto, ConstraintValidatorContext context) {
        final boolean isValid = reservationDto.getArrivalDate().isBefore(reservationDto.getDepartureDate());
        logger.info("action=isValid, isValid=" + isValid);
        return isValid;
    }
}