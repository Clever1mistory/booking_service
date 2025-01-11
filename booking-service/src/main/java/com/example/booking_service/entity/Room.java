package com.example.booking_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "rooms")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private int number;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int maxCapacity;

    @ElementCollection
    @CollectionTable(name = "unavailable_dates", joinColumns = @JoinColumn(name = "room_id"))
    @Column(name = "unavailable_date")
    @Builder.Default
    private List<Instant> unavailableDates = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;



    public void addUnavailableDate(Instant date) {
        this.unavailableDates.add(Instant.from(date));
    }

    public void removeUnavailableDate(Instant date) {
        this.unavailableDates.remove(date);
    }

    public boolean isAvailable(Instant checkIn, Instant checkOut) {
        for (Instant date : unavailableDates) {
            if (!date.isBefore(checkIn) && !date.isAfter(checkOut)) {
                return false;
            }
        }
        return true;
    }
}
