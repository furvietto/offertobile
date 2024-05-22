package com.example.rentalmanagement.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "apartment")
public class ApartmentEnt {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "APARTMENT_SEQ_GEN", sequenceName = "apartment_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APARTMENT_SEQ_GEN")
    private Integer id;

    @Column(nullable = false)
    @NotNull(message = "Address cannot be null")
    @Size(min = 1, max = 200, message = "Address must be between 1 and 200 characters")
    private String address;

    @Column(nullable = false)
    @NotNull(message = "Number of rooms cannot be null")
    @Min(value = 1, message = "Number of rooms must be at least 1")
    private Integer numberOfRooms;

    @Column(nullable = false)
    @NotNull(message = "Number of bathrooms cannot be null")
    @Min(value = 1, message = "Number of bathrooms must be at least 1")
    private Integer numberOfBathrooms;

    @Column(nullable = false)
    @NotNull(message = "Area cannot be null")
    @Positive(message = "Area must be positive")
    private Double area;

    //ciao
    @Column(nullable = false)
    @NotNull(message = "Price per night cannot be null")
    @Positive(message = "Price per night must be positive")
    private Double pricePerNight;

    @Size(max = 1000, message = "Description must be less than 1000 characters")
    private String description;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    @NotNull(message = "Owner ID cannot be null")
    private OwnerEnt owner;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    @NotNull(message = "Event ID cannot be null")
    private EventEnt event;

    @OneToMany(mappedBy = "apartment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PhotoEnt> photos;

    @OneToMany(mappedBy = "apartment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReservationEnt> reservations;
}
