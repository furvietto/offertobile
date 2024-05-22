package com.example.rentalmanagement.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "photo")
public class PhotoEnt {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "PHOTO_SEQ_GEN", sequenceName = "photo_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PHOTO_SEQ_GEN")
    private Integer id;

    @Column(nullable = false)
    @NotNull(message = "Photo cannot be null")
    @Size(min = 1, max = 500, message = "Photo must be between 1 and 500 characters")
    private String photo;

    @Size(max = 1000, message = "Description must be less than 1000 characters")
    private String description;

    @ManyToOne
    @JoinColumn(name = "apartment_id", nullable = false)
    @NotNull(message = "Apartment ID cannot be null")
    private ApartmentEnt apartment;
}
