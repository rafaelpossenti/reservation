package com.possenti.reservation.service;

import com.possenti.reservation.document.Reservation;
import com.possenti.reservation.dto.ReservationDto;
import com.possenti.reservation.exception.AnyReservationAvailableException;
import com.possenti.reservation.exception.ReservationNotFoundException;
import com.possenti.reservation.repository.ReservationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationService reservationService;

    @Test
    public void saveMustThrowAnyReservationAvailableExceptionWhenRangeIsBusy() {
        final ReservationDto dto = this.getReservationDto();

        List<Reservation> list = new ArrayList<>();
        list.add(dto.reservationDtoToReservation());

        when(reservationRepository.findForDateRange(any(), any())).thenReturn(list);

        Exception thrown = assertThrows(
                AnyReservationAvailableException.class,
                () -> reservationService.save(dto),
                "Expected save to throw AnyReservationAvailableException, but it didn't"
        );
        assertTrue(thrown.getMessage().contains("There is any reservation dates available from"));
    }

    @Test
    public void saveMustSaveWithSuccessWhenAllIsOk() {
        final ReservationDto dto = this.getReservationDto();

        List<Reservation> list = new ArrayList<>();

        when(reservationRepository.findForDateRange(any(), any())).thenReturn(list);
        when(reservationRepository.save(any())).thenReturn(dto.reservationDtoToReservation());

        reservationService.save(dto);
        Mockito.verify(reservationRepository, times(1)).save(any());
    }

    @Test
    public void updateMustThrowAnyReservationAvailableExceptionWhenRangeIsBusy() {
        final ReservationDto dto = this.getReservationDto();
        dto.setId("9999");
        final Reservation reservation = dto.reservationDtoToReservation();
        List<Reservation> list = new ArrayList<>();
        list.add(reservation);

        when(reservationRepository.findById(dto.getId())).thenReturn(Optional.of(reservation));
        when(reservationRepository.findForDateRange(any(), any())).thenReturn(list);

        Exception thrown = assertThrows(
                AnyReservationAvailableException.class,
                () -> reservationService.update("9999", dto),
                "Expected update to throw AnyReservationAvailableException, but it didn't"
        );
        assertTrue(thrown.getMessage().contains("There is any reservation dates available from"));
    }

    @Test
    public void updateMustThrowReservationNotFoundExceptionWhenThereIsNoReservation() {
        final ReservationDto dto = this.getReservationDto();
        dto.setId("9999");
        when(reservationRepository.findById(dto.getId())).thenReturn(Optional.empty());

        Exception thrown = assertThrows(
                ReservationNotFoundException.class,
                () -> reservationService.update("9999", dto),
                "Expected update to throw ReservationNotFoundException, but it didn't"
        );
        assertTrue(thrown.getMessage().contains("Account not found with id"));
    }

    @Test
    public void updateMustSaveWithSuccessWhenAllIsOk() {
        final ReservationDto dto = this.getReservationDto();
        dto.setId("9999");
        final Reservation reservation = dto.reservationDtoToReservation();
        List<Reservation> list = new ArrayList<>();

        when(reservationRepository.findForDateRange(any(), any())).thenReturn(list);
        when(reservationRepository.save(any())).thenReturn(reservation);

        reservationService.save(dto);
        Mockito.verify(reservationRepository, times(1)).save(any());
    }

    @Test
    public void deleteMustThrowReservationNotFoundExceptionWhenThereIsNoReservation() {
        when(reservationRepository.findById("9999")).thenReturn(Optional.empty());

        Exception thrown = assertThrows(
                ReservationNotFoundException.class,
                () -> reservationService.delete("9999"),
                "Expected update to throw ReservationNotFoundException, but it didn't"
        );
        assertTrue(thrown.getMessage().contains("Account not found with id"));
    }

    @Test
    public void deleteMustDeleteWithSuccessWhenAllIsOk() {
        final ReservationDto dto = this.getReservationDto();
        dto.setId("9999");
        final Reservation reservation = dto.reservationDtoToReservation();
        when(reservationRepository.findById("9999")).thenReturn(Optional.of(reservation));

        reservationService.delete("9999");
        Mockito.verify(reservationRepository, times(1)).deleteById("9999");
    }

    @Test
    public void getAvailableDaysMustReturnAListWithSomeBusyDays() {
        final ReservationDto dto = this.getReservationDto();

        List<Reservation> list = new ArrayList<>();
        list.add(dto.reservationDtoToReservation());

        when(reservationRepository.findForDateRange(any(), any())).thenReturn(list);

        List<LocalDate> availableDays = reservationService.getAvailableDays(LocalDate.now(), LocalDate.now().plusDays(10));
        assertEquals(9, availableDays.size());
    }

    @Test
    public void getAvailableDaysMustReturnAListWithNoBusyDays() {
        final ReservationDto dto = this.getReservationDto();

        List<Reservation> list = new ArrayList<>();

        when(reservationRepository.findForDateRange(any(), any())).thenReturn(list);

        List<LocalDate> availableDays = reservationService.getAvailableDays(LocalDate.now(), LocalDate.now().plusDays(10));
        assertEquals(11, availableDays.size());
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
