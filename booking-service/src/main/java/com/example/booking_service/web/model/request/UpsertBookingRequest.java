package com.example.booking_service.web.model.request;

import com.example.booking_service.validation.BookingDatesValid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDate;

@Data
@BookingDatesValid
public class UpsertBookingRequest {

    @NotNull(message = "Room ID is required")
    private Long roomId;

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Check-in date is required")
    @FutureOrPresent(message = "Check-in date must be future or present")
    private Instant checkInDate;

    @NotNull(message = "Check-out date is required")
    @Future(message = "Check-out date must be future")
    private Instant checkOutDate;
}
