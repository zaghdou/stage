package com.shounoop.carrentalspring.services.customer;

import com.shounoop.carrentalspring.dto.BookAImmobilierDto;
import com.shounoop.carrentalspring.dto.ImmobilierDto;
import com.shounoop.carrentalspring.dto.RatingDto;
import com.shounoop.carrentalspring.dto.RatingWithUserDto;

import java.util.List;

public interface CustomerService {
    List<ImmobilierDto> getAllCars();
    boolean bookACar(BookAImmobilierDto bookACarDto);
    ImmobilierDto getCarById(Long carId);
    boolean submitRating(RatingDto ratingDto, Long carId);
    List<RatingWithUserDto> getRatingsByCarId(Long carId);
    Double getAverageRating(Long carId);
    Long getTotalComments(Long carId);
    List<BookAImmobilierDto> getBookingsByUserId(Long userId);
}
