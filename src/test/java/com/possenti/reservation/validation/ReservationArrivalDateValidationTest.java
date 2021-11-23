package com.possenti.reservation.validation;

import com.possenti.reservation.dto.ReservationDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class ReservationArrivalDateValidationTest {

    @Mock
    private ConstraintValidatorContext context;

    @InjectMocks
    private ReservationArrivalDateValidation reservationArrivalDateValidation;

    @Test
    public void isValidMustReturnTrueWhenArrivalDateIsAfterTodayAndBeforeOneMonthFromNow() {
        ReservationDto dto = getReservationDto();
        boolean isValid = reservationArrivalDateValidation.isValid(dto, context);
        assertTrue(isValid);
    }

    @Test
    public void isValidMustReturnFalseWhenArrivalDateIsAfterOneMonthFromNow() {
        ReservationDto dto = getReservationDto();
        dto.setArrivalDate(LocalDate.now().plusMonths(1).plusDays(1));
        boolean isValid = reservationArrivalDateValidation.isValid(dto, context);
        assertFalse(isValid);
    }

    @Test
    public void isValidMustReturnFalseWhenArrivalDateIsBeforeToday() {
        ReservationDto dto = getReservationDto();
        dto.setArrivalDate(LocalDate.now());
        boolean isValid = reservationArrivalDateValidation.isValid(dto, context);
        assertFalse(isValid);
    }

    private ReservationDto getReservationDto() {
        final ReservationDto dto = new ReservationDto();
        dto.setEmail("gandalf@hotmail.com");
        dto.setName("Gandalf");
        dto.setArrivalDate(LocalDate.now().plusDays(1));
        dto.setDepartureDate(LocalDate.now().plusDays(3));
        return dto;
    }

}