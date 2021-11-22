package com.possenti.reservation.repository;

import com.possenti.reservation.document.Reservation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends MongoRepository<Reservation, String> {

    Reservation findByArrivalDate(LocalDate arrivalDate);

    Reservation findByDepartureDate(LocalDate departureDate);

    @Query("{$or: [" +
                        // 01-12 > 21-11    |        03-12 > 21-12
            "{$and: [ {arrivalDate: {$gt: ?0}}, {departureDate: {$gt: ?1}} ]}," +
                        // 03-12 > 21-11    |        03-12 <= 21-12
            "{$and: [ {departureDate: {$gt: ?0}}, {departureDate: {$lte: ?1}} ]}," +
                       //01-12 <= 21-11     |        01-12 <= 21-12
            "{$and: [ {arrivalDate: {$lte: ?0}}, {arrivalDate: {$lte: ?1}} ]}" +
            "]}")
    List<Reservation> findForDateRange(LocalDate startDate, LocalDate endDate);
}
