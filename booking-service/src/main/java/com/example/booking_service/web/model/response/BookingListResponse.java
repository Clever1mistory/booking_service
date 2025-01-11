package com.example.booking_service.web.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingListResponse {

    List<BookingResponse> bookings = new ArrayList<>();
    private long totalElements;
    private int totalPages;
    private int currentPage;
    private int pageSize;
}
