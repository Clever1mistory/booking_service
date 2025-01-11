package com.example.booking_service.validation;

import com.example.booking_service.web.model.request.UpsertBookingRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class BookingDatesValidator implements ConstraintValidator<BookingDatesValid, UpsertBookingRequest> {

    @Override
    public boolean isValid(UpsertBookingRequest request, ConstraintValidatorContext context) {
        if (request.getCheckInDate() == null || request.getCheckOutDate() == null) {
            return true;
        }

        return request.getCheckInDate().isBefore(request.getCheckOutDate());
    }
}
