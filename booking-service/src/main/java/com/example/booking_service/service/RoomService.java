package com.example.booking_service.service;

import com.example.booking_service.entity.Room;
import com.example.booking_service.web.model.filter.RoomFilter;
import com.example.booking_service.web.model.request.UpsertRoomRequest;
import com.example.booking_service.web.model.response.RoomListResponse;
import com.example.booking_service.web.model.response.RoomResponse;

import java.util.List;

public interface RoomService {

    RoomResponse findById(Long id);

    RoomResponse save(UpsertRoomRequest request, Long hotelId);

    RoomResponse update(Long id, UpsertRoomRequest request);

    void deleteById(Long id);

    RoomListResponse filterBy(RoomFilter filter);
}
