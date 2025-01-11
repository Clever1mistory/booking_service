package com.example.booking_service.service.impl;

import com.example.booking_service.entity.Booking;
import com.example.booking_service.entity.Room;
import com.example.booking_service.entity.User;
import com.example.booking_service.exception.EntityNotFoundException;
import com.example.booking_service.mapper.BookingMapper;
import com.example.booking_service.mapper.HotelMapper;
import com.example.booking_service.mapper.RoomMapper;
import com.example.booking_service.mapper.UserMapper;
import com.example.booking_service.repository.BookingRepository;
import com.example.booking_service.repository.RoomRepository;
import com.example.booking_service.service.BookingService;
import com.example.booking_service.service.HotelService;
import com.example.booking_service.service.RoomService;
import com.example.booking_service.service.UserService;
import com.example.booking_service.web.model.request.UpsertBookingRequest;
import com.example.booking_service.web.model.response.BookingListResponse;
import com.example.booking_service.web.model.response.BookingResponse;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final RoomService roomService;

    private final UserService userService;

    private final BookingRepository bookingRepository;

    private final BookingMapper bookingMapper;

    private final RoomMapper roomMapper;

    private final UserMapper userMapper;

    private final HotelService hotelService;

    private final HotelMapper hotelMapper;

    private final RoomRepository roomRepository;



    @Override
    public BookingResponse bookRoom(UpsertBookingRequest request) {

        Room room = roomMapper.responseToRoom(roomService.findById(request.getRoomId()), hotelService, hotelMapper);

        User user = userMapper.responseToUSer(userService.findById(request.getUserId()));

        if (!room.isAvailable(request.getCheckInDate(), request.getCheckOutDate())) {
            throw new IllegalArgumentException("Room is not available for the selected dates.");
        }

        Booking booking = bookingMapper.requestToBooking(request);

        booking.setRoom(room);
        booking.setUser(user);

        Booking savedBooking = bookingRepository.save(booking);

        addUnavailableDatesForRoom(room, request.getCheckInDate(), request.getCheckOutDate());

        return bookingMapper.bookingToResponse(savedBooking);
    }

    @Override
    public BookingListResponse findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return bookingMapper.bookingListToBookingListResponse(bookingRepository.findAll(pageable));
    }

    @Override
    public void deleteById(Long id) {

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found with id: " + id));

        // Получаем комнату, связанную с бронированием
        Room room = booking.getRoom();

        // Создаем список дат для удаления
        List<LocalDate> datesToRemove = booking.getCheckIn().atZone(ZoneId.systemDefault()).toLocalDate()
                .datesUntil(booking.getCheckOut().plusSeconds(86400).atZone(ZoneId.systemDefault()).toLocalDate())
                .toList();

        room.getUnavailableDates().removeAll(datesToRemove);

        // Сохраняем обновленную комнату
        roomRepository.save(room);

        bookingRepository.deleteById(id);
    }

    private void addUnavailableDatesForRoom(Room room, @NotNull(message = "Check-in date is required") @FutureOrPresent(message = "Check-in date must be future or present") Instant checkInDate, @NotNull(message = "Check-out date is required") @Future(message = "Check-out date must be future") Instant checkOutDate) {
        // Добавляем недоступные даты в диапазоне от checkInDate до checkOutDate включительно
        Instant date = checkInDate;
        while (!date.isAfter(checkOutDate)) {
            room.addUnavailableDate(date);  // Используем метод Room для добавления даты
            date = date.plusSeconds(86400);
        }

        roomRepository.save(room);
    }

}
