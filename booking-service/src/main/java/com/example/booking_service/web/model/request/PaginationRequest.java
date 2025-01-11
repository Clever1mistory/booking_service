package com.example.booking_service.web.model.request;

import lombok.Data;
import org.springframework.data.domain.PageRequest;

@Data
public class PaginationRequest {

    private int pageSize = 10;

    private int pageNumber = 0;

    public PageRequest pageRequest() {
        return PageRequest.of(pageNumber, pageSize);
    }
}
