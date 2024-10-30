package com.shounoop.carrentalspring.services.admin;

import com.shounoop.carrentalspring.dto.*;
import com.shounoop.carrentalspring.entity.Annonce;
import com.shounoop.carrentalspring.enums.BookAImmobilier;
import com.shounoop.carrentalspring.repository.BookACarRepository;
import com.shounoop.carrentalspring.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final CarRepository carRepository;
    private final BookACarRepository bookACarRepository;

    @Override
    public boolean postCar(ImmobilierDto carDto) throws IOException {
        boolean isSuccessful = false;

        try {
            Annonce car = new Annonce();
            car.setTitle(carDto.getTitle());
            car.setLocation(carDto.getLocation());
            car.setColor(carDto.getColor());
            car.setDescription(carDto.getDescription());
            car.setPrice(carDto.getPrice());

            car.setType(carDto.getType());
            car.setImage(carDto.getImage().getBytes());

            carRepository.save(car);

            isSuccessful = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return isSuccessful;
    }

    @Override
    public List<ImmobilierDto> getAllCars() {
        return carRepository.findAll().stream().map(Annonce::getCarDto).collect(Collectors.toList());
    }

    @Override
    public void deleteCar(Long id) {
        carRepository.deleteById(id);
    }

    @Override
    public ImmobilierDto getCarById(Long id) {
        return carRepository.findById(id).map(Annonce::getCarDto).orElse(null); // map() is a method that applies a given function to each element of a stream
    }

    @Override
    public boolean updateCar(Long id, ImmobilierDto carDto) throws IOException {
        Optional<Annonce> optionalCar = carRepository.findById(id);

        if (optionalCar.isPresent()) {
            Annonce existingCar = optionalCar.get();

            if (carDto.getImage() != null) {
                existingCar.setImage(carDto.getImage().getBytes());
            }

            existingCar.setPrice(carDto.getPrice());
            existingCar.setType(carDto.getType());
            existingCar.setDescription(carDto.getDescription());
            existingCar.setColor(carDto.getColor());
            existingCar.setTitle(carDto.getTitle());
            existingCar.setLocation(carDto.getLocation());

            carRepository.save(existingCar);

            return true;
        }

        return false;
    }

    @Override
    public List<BookAImmobilierDto> getBookings() {
        return bookACarRepository.findAll().stream().map(com.shounoop.carrentalspring.entity.BookAImmobilier::getBookACarDto).collect(Collectors.toList());
    }

    @Override
    public boolean changeBookingStatus(Long id, String status) {
        Optional<com.shounoop.carrentalspring.entity.BookAImmobilier> optionalBookACar = bookACarRepository.findById(id);

        if (optionalBookACar.isPresent()) {
            com.shounoop.carrentalspring.entity.BookAImmobilier bookACar = optionalBookACar.get();

            if (Objects.equals(status, "Approve")) {
                bookACar.setBookAImmobilier(BookAImmobilier.APPROVED);
            } else {
                bookACar.setBookAImmobilier(BookAImmobilier.REJECTED);
            }

            bookACarRepository.save(bookACar);

            return true;
        }

        return false;
    }

    @Override
    public CarDtoListDto searchCar(SearchCarDto searchCarDto) {
        Annonce car = new Annonce();
        car.setLocation(searchCarDto.getLocation());
        car.setType(searchCarDto.getType());
        car.setColor(searchCarDto.getColor());

        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll().withMatcher("brand", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase()).withMatcher("type", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase()).withMatcher("transmission", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase()).withMatcher("color", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());

        Example<Annonce> carExample = Example.of(car, exampleMatcher);

        List<Annonce> carList = carRepository.findAll(carExample);

        CarDtoListDto carDtoListDto = new CarDtoListDto();
        carDtoListDto.setCarDtoList(carList.stream().map(Annonce::getCarDto).collect(Collectors.toList()));

        return carDtoListDto;
    }
}
