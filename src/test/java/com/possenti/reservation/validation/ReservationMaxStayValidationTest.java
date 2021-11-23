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
public class ReservationMaxStayValidationTest {

    @Mock
    private ConstraintValidatorContext context;

    @InjectMocks
    private ReservationMaxStayValidation reservationMaxStayValidation;

    @Test
    public void isValidMustReturnTrueWhenStayIsLessThanThreeDays() {
        ReservationDto dto = getReservationDto();
        boolean isValid = reservationMaxStayValidation.isValid(dto, context);
        assertTrue(isValid);
    }

    @Test
    public void isValidMustReturnFalseWhenStayIsGreaterThanThreeDays() {
        ReservationDto dto = getReservationDto();
        dto.setDepartureDate(dto.getArrivalDate().plusDays(5));
        boolean isValid = reservationMaxStayValidation.isValid(dto, context);
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
