package com.example.booking_service.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.apache.logging.log4j.util.PerformanceSensitive;

import java.time.Instant;

@Entity
@Data
@Table(name = "booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Instant checkIn;

    private Instant checkOut;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
