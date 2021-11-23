package com.possenti.reservation.exception;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class RestResponseEntityExceptionHandlerTest {


    @InjectMocks
    private RestResponseEntityExceptionHandler restResponseEntityExceptionHandler;

    @Test
    public void handleMethodArgumentNotValidMustReturnAnHttpStatusBadRequest() {

        BindingResult errors = new BeanPropertyBindingResult(null, "reservation");
        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, errors);

        ResponseEntity<Object> objectResponseEntity = restResponseEntityExceptionHandler.handleMethodArgumentNotValid(ex, null,
                HttpStatus.BAD_REQUEST, null);
        assertEquals(HttpStatus.BAD_REQUEST, objectResponseEntity.getStatusCode());

    }

    @Test
    public void handleAnyReservationAvailableExceptionMustReturnAnHttpStatusBadRequest() {
        AnyReservationAvailableException ex = new AnyReservationAvailableException(LocalDate.now(), LocalDate.now());
        ResponseEntity<Object> objectResponseEntity = restResponseEntityExceptionHandler.handleAnyReservationAvailableException(ex);
        assertEquals(HttpStatus.BAD_REQUEST, objectResponseEntity.getStatusCode());
        Error error = (Error) objectResponseEntity.getBody();
        String message = String.format("There is any reservation dates available from %s to %s",
                LocalDate.now(), LocalDate.now());
        assertEquals(message, error.getErrors().get(0));
    }

    @Test
    public void handleReservationNotFoundExceptionMustReturnAnHttpStatusNotFound() {
        ReservationNotFoundException ex = new ReservationNotFoundException("156");
        ResponseEntity<Object> objectResponseEntity = restResponseEntityExceptionHandler.handleReservationNotFoundException(ex);
        assertEquals(HttpStatus.NOT_FOUND, objectResponseEntity.getStatusCode());
        Error error = (Error) objectResponseEntity.getBody();
        String message = "Account not found with id: 156";
        assertEquals(message, error.getErrors().get(0));
    }

}

