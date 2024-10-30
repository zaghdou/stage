package com.shounoop.carrentalspring.dto;

import lombok.Data;

@Data
public class RatingDto {
    private Long userId;
    private Integer rating; // Note de 1 à 5
    private String comment;
}
