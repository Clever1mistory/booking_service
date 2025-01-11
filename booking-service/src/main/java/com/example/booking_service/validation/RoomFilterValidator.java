package com.example.booking_service.validation;

import com.example.booking_service.web.model.filter.RoomFilter;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.ObjectUtils;

public class RoomFilterValidator implements ConstraintValidator<RoomFilterValid, RoomFilter> {

    @Override
    public boolean isValid(RoomFilter filter, ConstraintValidatorContext context) {
        if (!arePageAndSizeSpecified(filter, context)) {
            return false;
        }

        if (!isAtLeastOneFieldSpecified(filter, context)) {
            return false;
        }

        if (!areCheckInAndCheckOutDatesValid(filter, context)) {
            return false;
        }

        if (!isCheckOutDateAfterCheckInDate(filter, context)) {
            return false;
        }

        return true;
    }

    private boolean arePageAndSizeSpecified(RoomFilter filter, ConstraintValidatorContext context) {
        if (ObjectUtils.anyNull(filter.getPage(), filter.getSize())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Both page and size must be specified.")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }

    private boolean isAtLeastOneFieldSpecified(RoomFilter filter, ConstraintValidatorContext context) {
        if (ObjectUtils.allNull(filter.getId(), filter.getName(), filter.getMinPrice(), filter.getMaxPrice(),
                filter.getMaxOccupancy(), filter.getCheckInDate(), filter.getCheckOutDate(), filter.getHotelId())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("At least one of must be specified: id, name, minPrice, maxPrice, " +
                            "maxOccupancy, checkIn, checkOut, hotelId.")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }

    private boolean areCheckInAndCheckOutDatesValid(RoomFilter filter, ConstraintValidatorContext context) {
        if (ObjectUtils.anyNull(filter.getCheckInDate(), filter.getCheckOutDate())) {
            if (!ObjectUtils.isEmpty(filter.getCheckInDate()) || !ObjectUtils.isEmpty(filter.getCheckOutDate())) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("Both check-in and check-out dates must be specified together.")
                        .addConstraintViolation();
                return false;
            }
        }
        return true;
    }

    private boolean isCheckOutDateAfterCheckInDate(RoomFilter filter, ConstraintValidatorContext context) {
        if (filter.getCheckInDate() != null && filter.getCheckOutDate() != null) {
            if (filter.getCheckOutDate().isBefore(filter.getCheckInDate())) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("Check-out date must be later than check-in.")
                        .addConstraintViolation();
                return false;
            }
        }
        return true;
    }
}
