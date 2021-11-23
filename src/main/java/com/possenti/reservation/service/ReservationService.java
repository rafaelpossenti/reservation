package com.possenti.reservation.service;

import com.possenti.reservation.document.Reservation;
import com.possenti.reservation.dto.ReservationCodeDto;
import com.possenti.reservation.dto.ReservationDto;
import com.possenti.reservation.exception.AnyReservationAvailableException;
import com.possenti.reservation.exception.ReservationNotFoundException;
import com.possenti.reservation.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private ReadWriteLock lock = new ReentrantReadWriteLock();
    private Lock writeLock = lock.writeLock();

    @Autowired
    private ReservationRepository reservationRepository;

    @Retryable(include = CannotAcquireLockException.class,
            maxAttempts = 2, backoff = @Backoff(delay = 100))
    @Transactional
    public ReservationCodeDto save(ReservationDto reservationDto)  {
        try {
            writeLock.lock();
            final Reservation reservation = reservationDto.reservationDtoToReservation();
            validateIfDateRangeIsBusy(reservation);

            final String code = reservationRepository.save(reservation).getId();
            return new ReservationCodeDto(code);
        } finally {
            writeLock.unlock();
        }
    }

    @Transactional
    public ReservationCodeDto update(String reservationId, ReservationDto dto) {
        dto.setId(reservationId);
        this.findReservationById(reservationId);
        final Reservation reservation = dto.reservationDtoToReservation();

        validateIfDateRangeIsBusy(reservation);

        final String code = reservationRepository.save(reservation).getId();
        return new ReservationCodeDto(code);
    }

    @Transactional
    public void delete(String reservationId) {
        this.findReservationById(reservationId);
        reservationRepository.deleteById(reservationId);
    }

    @Transactional
    public List<LocalDate> getAvailableDays(LocalDate arrivalDate, LocalDate departureDate) {
        final LocalDate startDate = arrivalDate != null ? arrivalDate : LocalDate.now();
        final LocalDate endDate = departureDate != null ? departureDate : startDate.plusMonths(1);

        final List<LocalDate> availableDays = startDate.datesUntil(endDate.plusDays(1)).collect(Collectors.toList());

        final List<Reservation> reservations = reservationRepository.findForDateRange(startDate, endDate);

        reservations.forEach(b -> availableDays.removeAll(b.getAvailableDates()));
        return availableDays;
    }

    private Reservation findReservationById(String reservationId) {
        return reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationNotFoundException("Account not found with id: " + reservationId));
    }

    private void validateIfDateRangeIsBusy(Reservation reservation) {
        List<LocalDate> vacantDays = getAvailableDays(reservation.getArrivalDate(), reservation.getDepartureDate());
        if (!vacantDays.containsAll(reservation.getAvailableDates())) {
            throw new AnyReservationAvailableException(reservation.getArrivalDate(), reservation.getDepartureDate());
        }
    }

}
