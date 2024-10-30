package com.shounoop.carrentalspring.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RatingWithUserDto {
    private Long id;
    private Integer rating;
    private String comment;
    private String userName;
    private String userRole;
    private LocalDateTime createdAt;
}
