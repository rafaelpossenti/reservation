package com.possenti.reservation.service;

import com.possenti.reservation.document.Reservation;
import com.possenti.reservation.dto.ReservationDto;
import com.possenti.reservation.validation.ReservationValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
public class ReservationService {

    @Autowired
    private ReservationValidation reservationValidation;

    public void save(ReservationDto reservationDto, BindingResult result) {
        Reservation reservation = reservationDto.reservationDtoToReservation();
        reservationValidation.validate(reservation, result);
        if(result.hasErrors()) {
            result.getFieldErrors()
                    .forEach(fe -> System.out.println(fe.getField() + fe.getDefaultMessage()));
            System.out.println(result.getFieldErrors());
        }
    }

}
