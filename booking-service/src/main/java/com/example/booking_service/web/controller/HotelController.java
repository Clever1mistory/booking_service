package com.example.booking_service.web.controller;

import com.example.booking_service.service.HotelService;
import com.example.booking_service.web.model.request.UpsertHotelRequest;
import com.example.booking_service.web.model.response.HotelListResponse;
import com.example.booking_service.web.model.response.HotelResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/hotel")
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;


    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping
    public ResponseEntity<HotelListResponse> findAll(@RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(hotelService.findAll(page, size));
    }


    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("/{id}")
    public ResponseEntity<HotelResponse> findById(@PathVariable Long id) {

        return ResponseEntity.ok((hotelService.findById(id)));
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<HotelResponse> create(@RequestBody @Valid UpsertHotelRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(hotelService.save(request));
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<HotelResponse> update(@PathVariable("id") Long id,
                                                @RequestBody @Valid UpsertHotelRequest request) {

        return ResponseEntity.ok(hotelService.update(id, request));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        hotelService.deleteById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PutMapping("/{id}/rating")
    public ResponseEntity<String> addMark(@PathVariable Long id, @RequestParam double newMark) {

        hotelService.updateHotelRating(id, newMark);

        return ResponseEntity.ok("Mark is successfully added. Thank you!");

    }

}