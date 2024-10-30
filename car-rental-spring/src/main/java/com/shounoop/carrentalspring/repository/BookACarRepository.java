package com.shounoop.carrentalspring.repository;

import com.shounoop.carrentalspring.entity.BookAImmobilier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookACarRepository extends JpaRepository<BookAImmobilier, Long> {

    List<BookAImmobilier> findAllByUserId(Long userId);

    Optional<Object> findAllByCarId(Long carId);
}
