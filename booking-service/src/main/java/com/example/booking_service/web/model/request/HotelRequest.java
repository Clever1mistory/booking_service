package com.example.booking_service.web.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelRequest {

    private String name;

    private String title;

    private String city;

    private String address;

    private double distanceFromCityCenter;
}
