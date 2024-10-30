package com.shounoop.carrentalspring.controller;

import com.shounoop.carrentalspring.dto.ImmobilierDto;
import com.shounoop.carrentalspring.dto.SearchCarDto;
import com.shounoop.carrentalspring.services.AgentImmobiler.AgentImmobilierService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/agentimmob")
@RequiredArgsConstructor
public class AgentimmobilierController {
    private final AgentImmobilierService agentImmobilierService;

    @GetMapping("/cars")
    public ResponseEntity<List<ImmobilierDto>> getAllCars() {
        return ResponseEntity.ok(agentImmobilierService.getAllCars());
    }

    @DeleteMapping("/car/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
        agentImmobilierService.deleteCar(id);
        return ResponseEntity.ok(null);
    }
    @GetMapping("/car/{id}")
    public ResponseEntity<ImmobilierDto> getCarById(@PathVariable Long id) {
        ImmobilierDto carDto = agentImmobilierService.getCarById(id);
        return carDto != null ? ResponseEntity.ok(carDto) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping("/car/{id}")
    public ResponseEntity<Void> updateCar(@PathVariable Long id, @ModelAttribute ImmobilierDto carDto) throws IOException {
        boolean isSuccessful = agentImmobilierService.updateCar(id, carDto);
        return isSuccessful ? ResponseEntity.status(HttpStatus.OK).build() : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }



    @PutMapping("/car/booking/{bookingId}/{status}")
    public ResponseEntity<Void> changeBookingStatus(@PathVariable Long bookingId, @PathVariable String status) {
        boolean isSuccessful = agentImmobilierService.changeBookingStatus(bookingId, status);
        return isSuccessful ? ResponseEntity.status(HttpStatus.OK).build() : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping("/car/search")
    public ResponseEntity<List<ImmobilierDto>> searchCars(@RequestBody SearchCarDto searchCarDto) {

        return ResponseEntity.ok(agentImmobilierService.searchCars(searchCarDto));
    }

    }

