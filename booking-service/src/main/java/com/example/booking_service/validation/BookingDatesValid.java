package com.example.booking_service.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = BookingDatesValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface BookingDatesValid {

    String message() default "Check-out must be after check-in";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
