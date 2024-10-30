package com.shounoop.carrentalspring.repository;

import com.shounoop.carrentalspring.entity.Annonce;
import com.shounoop.carrentalspring.entity.BookAImmobilier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Annonce, Long> {
   // List<BookAImmobilier> findAllByCarId(Long carId);

}
