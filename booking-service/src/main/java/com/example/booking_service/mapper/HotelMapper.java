package com.example.booking_service.mapper;

import com.example.booking_service.entity.Hotel;
import com.example.booking_service.web.model.request.HotelRequest;
import com.example.booking_service.web.model.request.UpsertHotelRequest;
import com.example.booking_service.web.model.response.HotelListResponse;
import com.example.booking_service.web.model.response.HotelResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface HotelMapper {

    @Mapping(target = "id", ignore = true)
    Hotel requestToHotel(UpsertHotelRequest request);

    Hotel requestToHotel(Long id, UpsertHotelRequest request);

    Hotel responseToHotel(HotelResponse response);

    @Mapping(target = "reviewCount", source = "ratingCount")
    HotelResponse hotelToResponse(Hotel hotel);

    default HotelListResponse hotelListToHotelListResponse(Page<Hotel> hotelPage) {
        HotelListResponse response = new HotelListResponse();
        response.setHotels(hotelPage.getContent().stream()
                .map(this::hotelToResponse)
                .toList());

        response.setTotalElements(hotelPage.getTotalElements());
        response.setTotalPages(hotelPage.getTotalPages());
        response.setCurrentPage(hotelPage.getNumber());
        response.setPageSize(hotelPage.getSize());
        return response;
    }
}
