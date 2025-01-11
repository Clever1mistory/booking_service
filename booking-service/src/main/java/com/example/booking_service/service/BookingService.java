package com.example.booking_service.service;

import com.example.booking_service.web.model.request.UpsertBookingRequest;
import com.example.booking_service.web.model.response.BookingListResponse;
import com.example.booking_service.web.model.response.BookingResponse;


public interface BookingService {

    BookingResponse bookRoom(UpsertBookingRequest bookingRequest);

    BookingListResponse findAll(int page, int size);

    void deleteById(Long id);
}
