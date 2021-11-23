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
public class ReservationArrivalDateBeforeDepartureDateValidationTest {

    @Mock
    private ConstraintValidatorContext context;

    @InjectMocks
    private ReservationArrivalDateBeforeDepartureDateValidation reservationArrivalDateBeforeDepartureDateValidation;

    @Test
    public void isValidMustReturnTrueWhenArrivalDateIsBeforeDepartureDate() {
        ReservationDto dto = getReservationDto();
        boolean isValid = reservationArrivalDateBeforeDepartureDateValidation.isValid(dto, context);
        assertTrue(isValid);
    }

    @Test
    public void isValidMustReturnFalseWhenArrivalDateIsAfterDepartureDate() {
        ReservationDto dto = getReservationDto();
        dto.setArrivalDate(dto.getDepartureDate().plusDays(2));
        boolean isValid = reservationArrivalDateBeforeDepartureDateValidation.isValid(dto, context);
        assertFalse(isValid);
    }

    private ReservationDto getReservationDto() {
        final ReservationDto dto = new ReservationDto();
        dto.setEmail("gandalf@hotmail.com");
        dto.setName("Gandalf");
        dto.setArrivalDate(LocalDate.now());
        dto.setDepartureDate(LocalDate.now().plusDays(2));
        return dto;
    }

}
