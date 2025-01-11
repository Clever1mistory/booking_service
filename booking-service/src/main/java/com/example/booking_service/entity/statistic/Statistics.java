package com.example.booking_service.entity.statistic;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Document(collection = "statistics")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Statistics {
    @Id
    private String id;
    private Long userId;
    private Long roomId;
    private Instant checkIn;
    private Instant checkOut;
    private EventType eventType;
    private Instant eventTime;
}
