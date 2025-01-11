package com.example.booking_service.service.impl;

import com.example.booking_service.entity.Hotel;
import com.example.booking_service.entity.Room;
import com.example.booking_service.exception.EntityNotFoundException;
import com.example.booking_service.mapper.HotelMapper;
import com.example.booking_service.mapper.RoomMapper;
import com.example.booking_service.repository.RoomRepository;
import com.example.booking_service.repository.specification.RoomSpecification;
import com.example.booking_service.service.HotelService;
import com.example.booking_service.service.RoomService;
import com.example.booking_service.utils.BeanUtils;
import com.example.booking_service.web.model.filter.RoomFilter;
import com.example.booking_service.web.model.request.RoomRequest;
import com.example.booking_service.web.model.request.UpsertRoomRequest;
import com.example.booking_service.web.model.response.RoomListResponse;
import com.example.booking_service.web.model.response.RoomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;
    private final HotelService hotelService;
    private final HotelMapper hotelMapper;


    @Override
    public RoomResponse findById(Long id) {

        return roomMapper.roomToResponse(roomRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(MessageFormat.format("Room with ID {0} not found", id))));
    }

    @Override
    public RoomResponse save(UpsertRoomRequest request, Long hotelId) {

        Hotel hotel = hotelMapper.responseToHotel(hotelService.findById(hotelId));

        if (hotel == null) {
            System.out.println(":OAJSD:OJAS:DOJ:LASJKDL:J:ASLDJ:LASJD:LJASDL:!!!!!!!!!!!!!!!!!!!");
            throw new EntityNotFoundException("Hotel not found with id: " + hotelId);
        }

        Room room = roomMapper.requestToRoom(request);

        room.setHotel(hotel);

        return roomMapper.roomToResponse(roomRepository.save(room));
    }
    @Override
    public RoomResponse update(Long id, UpsertRoomRequest request) {

        Room existedRoom = roomMapper.responseToRoom(findById(id), hotelService, hotelMapper);
        Room updatedRoom = roomMapper.requestToRoom(id, request);

        BeanUtils.copyRoomNotNullProperties(updatedRoom, existedRoom);

        return roomMapper.roomToResponse(roomRepository.save(existedRoom));
    }


    @Override
    public void deleteById(Long id) {
        findById(id);
        roomRepository.deleteById(id);
    }

    @Override
    public RoomListResponse filterBy(RoomFilter filter) {
        return roomMapper.roomListToRoomListResponse(roomRepository.findAll(RoomSpecification.withFilter(filter),
                PageRequest.of(filter.getPage(), filter.getSize())));
    }
}
