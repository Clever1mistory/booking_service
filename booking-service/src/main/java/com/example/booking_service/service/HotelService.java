package com.example.booking_service.service;

import com.example.booking_service.entity.Hotel;
import com.example.booking_service.web.model.filter.HotelFilter;
import com.example.booking_service.web.model.request.UpsertHotelRequest;
import com.example.booking_service.web.model.response.HotelListResponse;
import com.example.booking_service.web.model.response.HotelResponse;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface HotelService {

    HotelListResponse findAll(int page, int size);

    HotelResponse findById(Long id);

    HotelResponse save(UpsertHotelRequest request);

    HotelResponse update(Long id, UpsertHotelRequest request);

    void deleteById(Long id);

    void updateHotelRating(Long id, double newMark);

    HotelListResponse filterBy(HotelFilter filter);
}
