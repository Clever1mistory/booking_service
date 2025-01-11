package com.example.booking_service.service;

import com.example.booking_service.event.BookingEvent;
import com.example.booking_service.event.UserRegistrationEvent;

import java.io.IOException;

public interface StatisticsService {

    String exportStatisticsToCSV() throws IOException;

    void handleUserRegistration(UserRegistrationEvent event);

    void handleBooking(BookingEvent event);
}
