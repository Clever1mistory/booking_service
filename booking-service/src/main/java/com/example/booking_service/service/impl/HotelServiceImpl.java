package com.example.booking_service.service.impl;

import com.example.booking_service.entity.Hotel;
import com.example.booking_service.exception.EntityNotFoundException;
import com.example.booking_service.mapper.HotelMapper;
import com.example.booking_service.repository.HotelRepository;
import com.example.booking_service.repository.specification.HotelSpecification;
import com.example.booking_service.service.HotelService;
import com.example.booking_service.utils.BeanUtils;
import com.example.booking_service.web.model.filter.HotelFilter;
import com.example.booking_service.web.model.request.UpsertHotelRequest;
import com.example.booking_service.web.model.response.HotelListResponse;
import com.example.booking_service.web.model.response.HotelResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;

    @Override
    public HotelListResponse filterBy(HotelFilter filter) {
        return hotelMapper.hotelListToHotelListResponse(hotelRepository.findAll(HotelSpecification.withFilter(filter), PageRequest.of(
                filter.getPage(), filter.getSize())));
    }

    @Override
    public HotelListResponse findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return hotelMapper.hotelListToHotelListResponse(hotelRepository.findAll(pageable));
    }

    @Override
    public HotelResponse findById(Long id) {
        return hotelMapper.hotelToResponse(hotelRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(MessageFormat.format("Hotel with ID {0} not found", id))));
    }

    @Override
    public HotelResponse save(UpsertHotelRequest request) {

        return hotelMapper.hotelToResponse(hotelRepository.save(hotelMapper.requestToHotel(request)));
    }

    @Override
    public HotelResponse update(Long id, UpsertHotelRequest request) {
        Hotel existedHotel = hotelMapper.responseToHotel(findById(id));
        Hotel updatedHotel = hotelMapper.requestToHotel(id, request);

        BeanUtils.copyHotelNotNullProperties(updatedHotel, existedHotel);

        return hotelMapper.hotelToResponse(hotelRepository.save(existedHotel));
    }


    @Override
    public void deleteById(Long id) {
        findById(id);
        hotelRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void updateHotelRating(Long id, double newMark) {

        if (newMark < 1 || newMark > 5) {
            throw new IllegalArgumentException("Mark should be from 1 to 5.");
        }

        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(MessageFormat.format("Hotel with ID {0} not found", id)));


        double currentRating = hotel.getRating();
        int numberOfRatings = hotel.getRatingCount();


        /*
        Вычисляем общую сумму всех оценок
        Обновляем сумму оценок с учетом новой оценки
        Обновляем средний рейтинг
        Увеличиваем количество оценок
         */
        double totalRating = currentRating * numberOfRatings;

        totalRating = totalRating - currentRating + newMark;

        numberOfRatings = numberOfRatings + 1;

        double newRating = Math.round((totalRating / numberOfRatings) * 10.0) / 10.0;

        hotel.setRatingCount(numberOfRatings);
        hotel.setRating(newRating);

        hotelRepository.save(hotel);

    }

}
