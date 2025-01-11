package com.example.booking_service.repository.specification;

import com.example.booking_service.entity.Hotel;
import com.example.booking_service.web.model.filter.HotelFilter;
import org.springframework.data.jpa.domain.Specification;

public interface HotelSpecification {

    static Specification<Hotel> withFilter(HotelFilter hotelFilter) {
        return Specification.where(byId(hotelFilter.getId()))
                .and(byName(hotelFilter.getName()))
                .and(byTitle(hotelFilter.getTitle()))
                .and(byCity(hotelFilter.getCity()))
                .and(byAddress(hotelFilter.getAddress()))
                .and(byDistanceFromCityCenter(hotelFilter.getDistanceFromCityCenter()))
                .and(byRating(hotelFilter.getRating()))
                .and(byRatingCount(hotelFilter.getReviewCount()));
    }

    static Specification<Hotel> byRating(Double rating) {

        return (root, query, criteriaBuilder) -> {
            if (rating == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get("rating"), rating);
        };
    }

    static Specification<Hotel> byRatingCount(Integer ratingCount) {
        return (root, query, criteriaBuilder) -> {
            if (ratingCount == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get("ratingCount"), ratingCount);
        };
    }

    static Specification<Hotel> byId(Long hotelId) {
        return (root, query, criteriaBuilder) -> {
            if (hotelId == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get("id"), hotelId);
        };
    }

    static Specification<Hotel> byDistanceFromCityCenter(Double distanceFromCityCenter) {
        return (root, query, criteriaBuilder) -> {
            if (distanceFromCityCenter == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get("distanceFromCityCenter"), distanceFromCityCenter);
        };
    }

    static Specification<Hotel> byTitle(String title) {
        return (root, query, criteriaBuilder) -> {
            if (title == null || title.trim().isEmpty()) {
                return null;
            }
            return criteriaBuilder.equal(root.get("title"), "%" +title + "%");
        };
    }

    static Specification<Hotel> byCity(String city) {
        return (root, query, criteriaBuilder) -> {
            if (city == null || city.trim().isEmpty()) {
                return null;
            }
            return criteriaBuilder.like(root.get("city"), "%" + city + "%");
        };
    }

    static Specification<Hotel> byAddress(String address) {
        return (root, query, criteriaBuilder) -> {
            if (address == null || address.trim().isEmpty()) {
                return null;
            }
            return criteriaBuilder.like(root.get("address"), "%" + address + "%");
        };
    }

    static Specification<Hotel> byName(String name) {
        return (root, query, criteriaBuilder) -> {
            if (name == null || name.trim().isEmpty()) {
                return null;
            }
            return criteriaBuilder.like(root.get("name"), "%" + name + "%");
        };
    }

}
