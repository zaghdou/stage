package com.shounoop.carrentalspring.services.AgentImmobiler;

import com.shounoop.carrentalspring.dto.ImmobilierDto;
import com.shounoop.carrentalspring.dto.SearchCarDto;
import com.shounoop.carrentalspring.entity.Annonce;
import com.shounoop.carrentalspring.repository.BookACarRepository;
import com.shounoop.carrentalspring.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class AgentImmobilierImp implements AgentImmobilierService{
    private final CarRepository carRepository ;
    private final BookACarRepository bookACarRepository;



    @Override
    public List<ImmobilierDto> getAllCars() {
        return carRepository.findAll().stream()
                .map(Annonce::getCarDto)
                .collect(Collectors.toList());
    }

    @Override
    public ImmobilierDto getCarById(Long id) {
        return carRepository.findById(id)
                .map(Annonce::getCarDto)
                .orElse(null);
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
    public void deleteCar(Long id) {
        carRepository.deleteById(id);
    }


    @Override
    public boolean changeBookingStatus(Long id, String status) {
        Optional<com.shounoop.carrentalspring.entity.BookAImmobilier> optionalBookACar = bookACarRepository.findById(id);

        if (optionalBookACar.isPresent()) {
            com.shounoop.carrentalspring.entity.BookAImmobilier bookACar = optionalBookACar.get();

            if (Objects.equals(status, "Approve")) {
                bookACar.setBookAImmobilier(com.shounoop.carrentalspring.enums.BookAImmobilier.APPROVED);
            } else {
                bookACar.setBookAImmobilier(com.shounoop.carrentalspring.enums.BookAImmobilier.REJECTED);
            }

            bookACarRepository.save(bookACar);

            return true;
        }

        return false;
    }

    @Override
    public List<ImmobilierDto> searchCars(SearchCarDto searchCarDto) {
        Annonce car = new Annonce();
        car.setLocation(searchCarDto.getLocation());
        car.setType(searchCarDto.getType());
        car.setColor(searchCarDto.getColor());

        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll()
                .withMatcher("location", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("type", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("color", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());

        Example<Annonce> carExample = Example.of(car, exampleMatcher);

        List<Annonce> carList = carRepository.findAll(carExample);

        return carList.stream().map(Annonce::getCarDto).collect(Collectors.toList());
    }
}
