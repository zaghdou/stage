package com.shounoop.carrentalspring.services.AgentImmobiler;

import com.shounoop.carrentalspring.dto.BookAImmobilierDto;
import com.shounoop.carrentalspring.dto.ImmobilierDto;
import com.shounoop.carrentalspring.dto.SearchCarDto;

import java.io.IOException;
import java.util.List;

public interface AgentImmobilierService {

    List<ImmobilierDto> getAllCars();
    ImmobilierDto getCarById(Long id);
    boolean updateCar(Long id, ImmobilierDto carDto) throws IOException;
    void deleteCar(Long id);

    boolean changeBookingStatus(Long bookingId, String status);
    List<ImmobilierDto> searchCars(SearchCarDto searchCarDto);
}
