package com.example.booking_service.service.impl;

import com.example.booking_service.entity.statistic.EventType;
import com.example.booking_service.entity.statistic.Statistics;
import com.example.booking_service.event.BookingEvent;
import com.example.booking_service.event.UserRegistrationEvent;
import com.example.booking_service.repository.StatisticsRepository;
import com.example.booking_service.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    @Value("${app.kafka.bookings}")
    private String roomBookingsTopic;

    @Value("${app.kafka.userRegistrations}")
    private String userRegistrationsTopic;

    @Value("${app.kafka.groupId}")
    private String groupId;

    private final StatisticsRepository statisticRepository;

    @Override
    public String exportStatisticsToCSV() throws IOException {
        List<Statistics> statistics = statisticRepository.findAll();
        StringBuilder csvBuilder = new StringBuilder();

        // Добавляем заголовки CSV
        csvBuilder.append("User ID, Room ID, Check In, Check Out, Event Type, Event Time\n");

        // Заполняем строки данными
        for (Statistics statistic : statistics) {
            csvBuilder.append(statistic.getUserId() != null ? statistic.getUserId().toString() : "-")
                    .append(",")
                    .append(statistic.getRoomId() != null ? statistic.getRoomId().toString() : "-")
                    .append(",")
                    .append(statistic.getCheckIn() != null ? statistic.getCheckIn().toString() : "-")
                    .append(",")
                    .append(statistic.getCheckOut() != null ? statistic.getCheckOut().toString() : "-")
                    .append(",")
                    .append(statistic.getEventType() != null ? statistic.getEventType().toString() : "-")
                    .append(",")
                    .append(statistic.getEventTime() != null ? statistic.getEventTime().toString() : "-")
                    .append("\n");
        }

        return csvBuilder.toString();
    }

    @Override
    public void handleUserRegistration(UserRegistrationEvent event) {
        Statistics statistics = new Statistics();
        statistics.setUserId(event.getUserId());
        statistics.setEventType(EventType.USER_REGISTRATION);
        statistics.setEventTime(event.getEventTime());

        statisticRepository.save(statistics);
    }

    @Override
    public void handleBooking(BookingEvent event) {
        Statistics statistics = new Statistics();
        statistics.setRoomId(event.getRoomId());
        statistics.setCheckIn(event.getCheckIn());
        statistics.setCheckIn(event.getCheckOut());
        statistics.setEventType(EventType.BOOKING);
        statistics.setEventTime(event.getEventTime());

        statisticRepository.save(statistics);
    }
}
