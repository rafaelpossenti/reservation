package com.possenti.reservation.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ReservationArrivalDateBeforeDepartureDateValidation.class})
@Documented
public @interface ReservationArrivalDateBeforeDepartureDate {
    String message() default "Reservation arrival date must be before than departure date.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
