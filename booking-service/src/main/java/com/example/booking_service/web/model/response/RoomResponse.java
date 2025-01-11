package com.example.booking_service.web.model.response;

import lombok.Data;

import java.time.Instant;

@Data
public class RoomResponse {

    private Long id;

    private String name;

    private String description;

    private int number;

    private int price;

    private int maxCapacity;

    private Instant unavailableDateBegin;

    private Instant unavailableDateEnd;

    private HotelResponse hotelResponse;

    private Long hotelId;
}
