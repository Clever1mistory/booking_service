package com.example.booking_service.repository;

import com.example.booking_service.entity.statistic.Statistics;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StatisticsRepository extends MongoRepository<Statistics, Long> {
}
