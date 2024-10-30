package com.shounoop.carrentalspring.entity;

import com.shounoop.carrentalspring.dto.ImmobilierDto;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "Annonces")
public class Annonce {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String color;
    private String location;
    private String type;
    private String description;
    private Long price;

    @Column(columnDefinition = "longblob")
    private byte[] image;

    @OneToMany(mappedBy = "car") // Correspond au champ dans Rating
    private List<Rating> ratings;

    public ImmobilierDto getCarDto() {
        ImmobilierDto carDto = new ImmobilierDto();
        carDto.setId(id);
        carDto.setTitle(title);
        carDto.setLocation(location);
        carDto.setColor(color);
        carDto.setPrice(price);
        carDto.setDescription(description);
        carDto.setType(type);
        carDto.setReturnedImage(image);
        return carDto;
    }
}
