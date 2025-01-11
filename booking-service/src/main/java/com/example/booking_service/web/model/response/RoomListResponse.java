package com.example.booking_service.web.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomListResponse {

    private List<RoomResponse> rooms = new ArrayList<>();

    private long totalElements;

    private int totalPages;

    private int currentPage;

    private int pageSize;
}
