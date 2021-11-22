package com.possenti.reservation.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ReservationMaxStayValidation.class})
@Documented
public @interface ReservationMaxStay {
    String message() default "The campsite can be reserved for max 3 days.";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
