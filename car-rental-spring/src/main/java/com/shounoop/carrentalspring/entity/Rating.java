package com.shounoop.carrentalspring.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "ratings")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "car_id", nullable = false) // Correspond à la colonne de la clé étrangère
    private Annonce car;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // Correspond à la colonne de la clé étrangère
    private User user;

    @Column(nullable = false)
    private Integer rating; // Note de 1 à 5

    @Column(columnDefinition = "TEXT")
    private String comment;

    @Column(name = "created_at", updatable = false, nullable = false)
    private java.time.LocalDateTime createdAt = java.time.LocalDateTime.now();
}
