package com.example.booking_service.web.controller;

import com.example.booking_service.service.BookingService;
import com.example.booking_service.web.model.request.UpsertBookingRequest;
import com.example.booking_service.web.model.response.BookingListResponse;
import com.example.booking_service.web.model.response.BookingResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/booking")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<BookingListResponse> findAll(@RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(bookingService.findAll(page, size));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PostMapping
    public ResponseEntity<BookingResponse> bookRoom(@RequestBody @Valid UpsertBookingRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED).body(bookingService.bookRoom(request));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookingService.deleteById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
