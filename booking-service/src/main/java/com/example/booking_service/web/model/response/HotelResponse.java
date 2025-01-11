package com.example.booking_service.web.model.response;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelResponse {

    private Long id;

    private String name;

    private String title;

    private String city;

    private String address;

    @Positive(message = "Distance from center must be positive")
    private double distanceFromCityCenter;

    @Min(value = 1, message = "Rating must be between 1 and 5")
    @Max(value = 5, message = "Rating must be between 1 and 5")
    private double rating;

    @Positive(message = "Review counts must be positive")
    private int reviewCount;
}
