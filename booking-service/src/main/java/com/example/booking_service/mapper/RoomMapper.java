package com.example.booking_service.mapper;

import com.example.booking_service.entity.Room;
import com.example.booking_service.service.HotelService;
import com.example.booking_service.web.model.request.UpsertRoomRequest;
import com.example.booking_service.web.model.response.RoomListResponse;
import com.example.booking_service.web.model.response.RoomResponse;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = HotelMapper.class)
public interface RoomMapper {
    @Mapping(target = "id", ignore = true)
    Room requestToRoom(UpsertRoomRequest request);

    Room requestToRoom(Long id, UpsertRoomRequest request);

    @Mapping(source = "hotel.id", target = "hotelId")
    RoomResponse roomToResponse(Room room);

    @Mapping(target = "hotel", expression = "java(hotelMapper.responseToHotel(hotelService.findById(response.getHotelId())))")
    Room responseToRoom(RoomResponse response, @Context HotelService hotelService, @Context HotelMapper hotelMapper);

    default RoomListResponse roomListToRoomListResponse(Page<Room> roomPage) {
        RoomListResponse response = new RoomListResponse();
        response.setRooms(roomPage.getContent().stream()
                .map(this::roomToResponse)
                .toList());

        response.setTotalElements(roomPage.getTotalElements());
        response.setTotalPages(roomPage.getTotalPages());
        response.setCurrentPage(roomPage.getNumber());
        response.setPageSize(roomPage.getSize());
        return response;
    }
}
