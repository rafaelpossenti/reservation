package com.possenti.reservation.controller;

import com.possenti.reservation.dto.ReservationDto;
import com.possenti.reservation.service.ReservationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ReservationControllerTest {

    @Mock
    private ReservationService reservationService;

    @InjectMocks
    private ReservationController reservationController;

    @Test
    public void getAvailableDatesMustCallServiceGetAvailableDatesOnce() {
        reservationController.getAvailableDates(LocalDate.now(), LocalDate.now());
        verify(reservationService, times(1)).getAvailableDays(LocalDate.now(), LocalDate.now());
    }

    @Test
    public void createMustCallServiceSaveOnce() {
        final ReservationDto dto = this.getReservationDto();
        reservationController.create(dto);
        verify(reservationService, times(1)).save(dto);
    }

    @Test
    public void updateMustCallServiceupdateOnce() {
        final ReservationDto dto = this.getReservationDto();
        reservationController.update( "9999", dto);
        verify(reservationService, times(1)).update("9999", dto);
    }

    @Test
    public void cancelMustCallServiceDeleteOnce() {
        reservationController.cancel("999");
        verify(reservationService, times(1)).delete("999");
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
