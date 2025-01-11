package com.example.booking_service.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingEvent {

    private Long userId;

    private Long roomId;

    private Instant checkIn;

    private Instant checkOut;

    private Instant eventTime;
}
