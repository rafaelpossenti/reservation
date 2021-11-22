package com.possenti.reservation.exception;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {

        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        errors.addAll(ex.getBindingResult().getGlobalErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList()));

        Error error = new Error(
                HttpStatus.BAD_REQUEST,
                LocalDateTime.now(),
                "Validation error",
                errors);

        return buildResponseEntity(error);
    }

    @ExceptionHandler(AnyReservationAvailableException.class)
    protected ResponseEntity<Object> handleAnyReservationAvailableException(AnyReservationAvailableException ex) {

        return buildResponseEntityForLocalizedMessage(ex.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ReservationNotFoundException.class)
    protected ResponseEntity<Object> handleReservationNotFoundException(ReservationNotFoundException ex) {
        return buildResponseEntityForLocalizedMessage(ex.getLocalizedMessage(), HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<Object> buildResponseEntityForLocalizedMessage(
            String localizedMessage,
            HttpStatus status
    ) {
        List<String> errors = Stream.of(localizedMessage)
                .collect(Collectors.toList());

        Error error = new Error(
                status,
                LocalDateTime.now(),
                "Validation error",
                errors);

        return buildResponseEntity(error);
    }

    private ResponseEntity<Object> buildResponseEntity(Error error) {
        return new ResponseEntity<>(error, error.getStatus());
    }

}
