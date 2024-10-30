package com.shounoop.carrentalspring.services.customer;

import com.shounoop.carrentalspring.dto.BookAImmobilierDto;
import com.shounoop.carrentalspring.dto.ImmobilierDto;
import com.shounoop.carrentalspring.dto.RatingDto;
import com.shounoop.carrentalspring.dto.RatingWithUserDto;
import com.shounoop.carrentalspring.entity.Annonce;
import com.shounoop.carrentalspring.entity.Rating;
import com.shounoop.carrentalspring.entity.User;
import com.shounoop.carrentalspring.repository.CarRepository;
import com.shounoop.carrentalspring.repository.RatingRepository;
import com.shounoop.carrentalspring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CarRepository annonceRepository;
    private final RatingRepository ratingRepository;
    private final UserRepository userRepository;

    @Override
    public List<ImmobilierDto> getAllCars() {
        return annonceRepository.findAll().stream()
                .map(Annonce::getCarDto)
                .collect(Collectors.toList());
    }

    @Override
    public boolean bookACar(BookAImmobilierDto bookACarDto) {
        // Implémentez la logique pour réserver une voiture
        return true;
    }

    @Override
    public ImmobilierDto getCarById(Long carId) {
        return annonceRepository.findById(carId)
                .map(Annonce::getCarDto)
                .orElse(null);
    }

    @Override
    public boolean submitRating(RatingDto ratingDto, Long carId) {
        User user = userRepository.findById(ratingDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Annonce car = annonceRepository.findById(carId)
                .orElseThrow(() -> new RuntimeException("Car not found"));

        Rating rating = new Rating();
        rating.setUser(user);
        rating.setCar(car);
        rating.setRating(ratingDto.getRating());
        rating.setComment(ratingDto.getComment());
        rating.setCreatedAt(java.time.LocalDateTime.now());

        ratingRepository.save(rating);
        return true;
    }

    @Override
    public List<RatingWithUserDto> getRatingsByCarId(Long carId) {
        return ratingRepository.findByCarId(carId).stream()
                .map(r -> {
                    RatingWithUserDto dto = new RatingWithUserDto();
                    dto.setId(r.getId());
                    dto.setRating(r.getRating());
                    dto.setComment(r.getComment());
                    dto.setUserName(r.getUser().getName());
                    dto.setCreatedAt(r.getCreatedAt());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Double getAverageRating(Long carId) {
        return ratingRepository.findAverageRatingByCarId(carId);
    }

    @Override
    public Long getTotalComments(Long carId) {
        return ratingRepository.countByCarId(carId);
    }

    @Override
    public List<BookAImmobilierDto> getBookingsByUserId(Long userId) {
        // Implémentez la logique pour récupérer les réservations d'un utilisateur
        return List.of();
    }
}
