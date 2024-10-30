package com.shounoop.carrentalspring.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
public class ImmobilierDto {
    private Long id;
    private String Location;
    private String color;
    private String title;
    private String type;
    private String description;
    private Long price;

    private MultipartFile image;
    private byte[] returnedImage;
}
