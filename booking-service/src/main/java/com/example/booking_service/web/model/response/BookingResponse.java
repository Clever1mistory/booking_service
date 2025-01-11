package com.example.booking_service.web.model.response;

import com.example.booking_service.entity.Room;
import com.example.booking_service.entity.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponse {

    private Long bookingId;

    private Instant checkIn;

    private Instant checkOut;

    private Long roomId;

    private Long userId;
}
