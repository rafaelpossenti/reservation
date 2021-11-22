package com.possenti.reservation.service;

import com.possenti.reservation.document.Reservation;
import com.possenti.reservation.dto.RangeDateDto;
import com.possenti.reservation.dto.ReservationDto;
import com.possenti.reservation.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Transactional
    public void save(ReservationDto reservationDto, BindingResult bindingResults) throws MethodArgumentNotValidException {
        final Reservation reservation = reservationDto.reservationDtoToReservation();
        final RangeDateDto rangeDateDto = new RangeDateDto(reservationDto.getArrivalDate(), reservation.getDepartureDate());
        List<LocalDate> vacantDays = getAvailabilityDays(rangeDateDto);

        if (!vacantDays.containsAll(reservation.getBookingDates())) {
            String message = String.format("No vacant dates available from %s to %s",
                    reservation.getArrivalDate(), reservation.getDepartureDate());
            bindingResults.addError(new FieldError("Reservation", "arrivalDate", message));
            throw new MethodArgumentNotValidException(null,bindingResults);
        }

        final String code = reservationRepository.save(reservation).getEmail();
        System.out.println(code);
    }

    public List<LocalDate> getAvailabilityDays(RangeDateDto rangeDateDto) {
        LocalDate startDate;
        LocalDate endDate;
        if (rangeDateDto.getArrivalDate() == null) {
            rangeDateDto.setArrivalDate(LocalDate.now());
        }
        if (rangeDateDto.getDepartureDate() == null) {
            rangeDateDto.setDepartureDate(rangeDateDto.getArrivalDate().plusMonths(1));
        }
        startDate = rangeDateDto.getArrivalDate();
        endDate = rangeDateDto.getDepartureDate();


        List<LocalDate> vacantDays = startDate.datesUntil(endDate.plusDays(1)).collect(Collectors.toList());

        List<Reservation> reservations = reservationRepository.findForDateRange(rangeDateDto.getArrivalDate(), rangeDateDto.getDepartureDate());
        System.out.println(reservations);

        reservations.forEach(b -> vacantDays.removeAll(b.getBookingDates()));
        return vacantDays;


//        final AvailabilityDto availabilityDto = new AvailabilityDto();
//        final List<LocalDate> dates = new ArrayList<>();
//        for (LocalDate date = rangeDateDto.getArrivalDate(); date.isEqual(rangeDateDto.getDepartureDate()) || date.isBefore(rangeDateDto.getDepartureDate()); date = date.plusDays(1)) {
//            final Reservation reservDb = reservationRepository.findByArrivalDate(date);
//            if (reservDb == null) {
//                dates.add(date);
//            } else {
//                int days = reservDb.getDepartureDate().getDayOfYear() - reservDb.getArrivalDate().getDayOfYear();
//                date.plusDays(days - 1);
//            }
//        }
//        availabilityDto.setDates(dates);
//        System.out.println(dates);
//        return availabilityDto;
    }

//    boolean isDateRangeTaken ( List <Reservation> reservationList , LocalDateRange dateRange )
//    {
//        boolean taken = false;
//        for ( Reservation reservation : reservationList )
//        {
//            LocalDateRange reservationRange = LocalDateRange.of(reservation.getArrivalDate(), reservation.getDepartureDate());
//            if (reservationRange.overlaps( dateRange ) ) { return true; }
//        }
//        return taken;
//    }
}
