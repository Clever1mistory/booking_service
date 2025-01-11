package com.example.booking_service.validation;

import com.example.booking_service.web.model.filter.HotelFilter;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.ObjectUtils;

public class HotelFilterValidator implements ConstraintValidator<HotelFilterValid, HotelFilter> {

    @Override
    public boolean isValid(HotelFilter filter, ConstraintValidatorContext context) {
        if (!arePageAndSizeSpecified(filter, context)) {
            return false;
        }

        if (!isAtLeastOneFieldSpecified(filter, context)) {
            return false;
        }

        return true;
    }

    private boolean arePageAndSizeSpecified(HotelFilter filter, ConstraintValidatorContext context) {
        if (ObjectUtils.anyNull(filter.getPage(), filter.getSize())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Both page and size must be specified.")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }

    private boolean isAtLeastOneFieldSpecified(HotelFilter filter, ConstraintValidatorContext context) {
        if (ObjectUtils.allNull(filter.getId(), filter.getName(), filter.getCity(), filter.getAddress(),
                filter.getTitle(), filter.getReviewCount(), filter.getRating(), filter.getDistanceFromCityCenter())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("At least one of must be specified: " +
                            "id, name, city, address, announcement, rating, reviewCount, distanceFromCityCenter.")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}

