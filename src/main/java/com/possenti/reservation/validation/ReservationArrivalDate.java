package com.possenti.reservation.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ReservationArrivalDateValidation.class})
@Documented
public @interface ReservationArrivalDate {

    String message() default "The campsite can be reserved minimum 1 day(s) ahead of arrival and up to 1 month in advance.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
