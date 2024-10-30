package com.shounoop.carrentalspring.services.admin;

import com.shounoop.carrentalspring.dto.*;

import java.io.IOException;
import java.util.List;

public interface AdminService {

    boolean postCar(ImmobilierDto carDto) throws IOException;

    List<ImmobilierDto> getAllCars();

    void deleteCar(Long id);

    ImmobilierDto getCarById(Long id);

    boolean updateCar(Long id, ImmobilierDto carDto) throws IOException;

    List<BookAImmobilierDto> getBookings();


    boolean changeBookingStatus(Long id, String status);

    CarDtoListDto searchCar(SearchCarDto searchCarDto);



}
