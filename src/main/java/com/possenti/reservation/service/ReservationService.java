package com.possenti.reservation.service;

import com.possenti.reservation.document.Reservation;
import com.possenti.reservation.dto.ReservationDto;
import com.possenti.reservation.exception.AnyReservationAvailableException;
import com.possenti.reservation.exception.ReservationNotFoundException;
import com.possenti.reservation.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Transactional
    public String save(ReservationDto reservationDto, BindingResult bindingResults) throws MethodArgumentNotValidException {
        final Reservation reservation = reservationDto.reservationDtoToReservation();

        validateIfDateRangeIsBusy(reservation, bindingResults);

        return reservationRepository.save(reservation).getId();
    }

    @Transactional
    public String update(String reservationId, ReservationDto dto, BindingResult bindingResults) throws MethodArgumentNotValidException {
        dto.setId(reservationId);
        this.findReservationById(reservationId);
        final Reservation reservation = dto.reservationDtoToReservation();

        validateIfDateRangeIsBusy(reservation, bindingResults);

        return reservationRepository.save(reservation).getId();
    }

    @Transactional
    public void delete(String reservationId)  {
        this.findReservationById(reservationId);
        reservationRepository.deleteById(reservationId);
    }

    public List<LocalDate> getAvailabilityDays(LocalDate arrivalDate, LocalDate departureDate) {
        final LocalDate startDate = arrivalDate != null ? arrivalDate : LocalDate.now();
        final LocalDate endDate = departureDate != null ? departureDate : startDate.plusMonths(1);

        List<LocalDate> vacantDays = startDate.datesUntil(endDate.plusDays(1)).collect(Collectors.toList());

        List<Reservation> reservations = reservationRepository.findForDateRange(startDate, endDate);
        System.out.println(reservations);

        reservations.forEach(b -> vacantDays.removeAll(b.getBookingDates()));
        return vacantDays;

    }

    private Reservation findReservationById(String reservationId) {
        return reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationNotFoundException("Account not found with id: " + reservationId));
    }

    private void validateIfDateRangeIsBusy(Reservation reservation, BindingResult bindingResults) throws MethodArgumentNotValidException {
        List<LocalDate> vacantDays = getAvailabilityDays(reservation.getArrivalDate(), reservation.getDepartureDate());
        if (!vacantDays.containsAll(reservation.getBookingDates())) {
            throw new AnyReservationAvailableException(reservation.getArrivalDate(), reservation.getDepartureDate());
        }
    }

}
