package com.shounoop.carrentalspring.dto;

import com.shounoop.carrentalspring.enums.BookAImmobilier;
import lombok.Data;

import java.security.cert.Extension;
import java.util.Date;

@Data
public class BookAImmobilierDto {


    private Long id;
    private Date fromDate;
    private Date toDate;
    private Long days;
    private Long price;
    private BookAImmobilier BookAImmobilier;
    private Long ImmobilierId;
    private Long userId;
    private String username;
    private String email;



}
