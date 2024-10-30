package com.shounoop.carrentalspring.controller;

import com.shounoop.carrentalspring.dto.BookAImmobilierDto;
import com.shounoop.carrentalspring.dto.ImmobilierDto;
import com.shounoop.carrentalspring.dto.RatingDto;
import com.shounoop.carrentalspring.dto.RatingWithUserDto;
import com.shounoop.carrentalspring.services.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping("/cars")
    public ResponseEntity<List<ImmobilierDto>> getAllCars() {
        List<ImmobilierDto> cars = customerService.getAllCars();
        return ResponseEntity.ok(cars);
    }

    @PostMapping("/car/book")
    public ResponseEntity<Void> bookACar(@RequestBody BookAImmobilierDto bookACarDto) {
        boolean isSuccessful = customerService.bookACar(bookACarDto);
        return isSuccessful ? ResponseEntity.status(HttpStatus.CREATED).build() : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/car/{carId}")
    public ResponseEntity<ImmobilierDto> getCarById(@PathVariable Long carId) {
        ImmobilierDto carDto = customerService.getCarById(carId);
        return carDto != null ? ResponseEntity.ok(carDto) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping("/car/{carId}/ratings")
    public ResponseEntity<Void> submitRating(@PathVariable Long carId, @RequestBody RatingDto ratingDto) {
        if (carId == null || ratingDto == null || ratingDto.getRating() == null || ratingDto.getUserId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        boolean isSuccessful = customerService.submitRating(ratingDto, carId);
        return isSuccessful ? ResponseEntity.status(HttpStatus.CREATED).build() : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/car/{carId}/ratings")
    public ResponseEntity<List<RatingWithUserDto>> getRatingsByCarId(@PathVariable Long carId) {
        List<RatingWithUserDto> ratings = customerService.getRatingsByCarId(carId);
        return ratings != null ? ResponseEntity.ok(ratings) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/car/{carId}/average-rating")
    public ResponseEntity<Double> getAverageRating(@PathVariable Long carId) {
        System.out.println("Getting average rating for car ID: " + carId);  // Journalisation
        Double averageRating = customerService.getAverageRating(carId);
        return averageRating != null ? ResponseEntity.ok(averageRating) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }


    @GetMapping("/car/{carId}/total-comments")
    public ResponseEntity<Long> getTotalComments(@PathVariable Long carId) {
        Long totalComments = customerService.getTotalComments(carId);
        return totalComments != null ? ResponseEntity.ok(totalComments) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/car/bookings/{userId}")
    public ResponseEntity<List<BookAImmobilierDto>> getBookingsByUserId(@PathVariable Long userId) {
        List<BookAImmobilierDto> bookings = customerService.getBookingsByUserId(userId);
        return bookings != null ? ResponseEntity.ok(bookings) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
