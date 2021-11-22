package com.possenti.reservation.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ReservationMaxStayValidation.class})
@Documented
public @interface ReservationMaxStay {
    String message() default "The campsite can be reserved for max 3 days.";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
