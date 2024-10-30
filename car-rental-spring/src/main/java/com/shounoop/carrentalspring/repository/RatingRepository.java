package com.shounoop.carrentalspring.repository;

import com.shounoop.carrentalspring.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    List<Rating> findByCarId(Long carId);
    Double findAverageRatingByCarId(Long carId);
    Long countByCarId(Long carId);
}
