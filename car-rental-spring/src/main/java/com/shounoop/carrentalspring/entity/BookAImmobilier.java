package com.shounoop.carrentalspring.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shounoop.carrentalspring.dto.BookAImmobilierDto;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;


@Entity
@Data
public class BookAImmobilier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date fromDate;
    private Date toDate;
    private Long days;
    private Long price;
    private com.shounoop.carrentalspring.enums.BookAImmobilier BookAImmobilier;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore // @JsonIgnore is used to ignore the user field when serializing the object to JSON.
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "car_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Annonce car;

    public BookAImmobilierDto getBookACarDto() {
        BookAImmobilierDto bookACarDto = new BookAImmobilierDto();
        bookACarDto.setId(this.id);
        bookACarDto.setDays(this.days);
        bookACarDto.setBookAImmobilier(this.BookAImmobilier);
        bookACarDto.setPrice(this.price);
        bookACarDto.setToDate(this.toDate);
        bookACarDto.setFromDate(this.fromDate);
        bookACarDto.setEmail(this.user.getEmail());
        bookACarDto.setUsername(this.user.getName());
        bookACarDto.setUserId(this.user.getId());
        bookACarDto.setImmobilierId(this.car.getId());
        return bookACarDto;
    }
}
