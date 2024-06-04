package com.example.rentalmanagement.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "event")
public class EventEnt {

    //@Serial
    //private static final long serialVersionUID = 1L;

    //@SequenceGenerator(name = "EVENT_SEQ_GEN", sequenceName = "event_sequence", allocationSize = 1)
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EVENT_SEQ_GEN")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    @NotNull(message = "Event name cannot be null")
    @Size(min = 1, max = 200, message = "Event name must be between 1 and 200 characters")
    private String eventName;

    @Size(max = 1000, message = "Description must be less than 1000 characters")
    private String description;

    @Column(nullable = false)
    @NotNull(message = "Start date cannot be null")
    @FutureOrPresent(message = "Start date must be in the present or future")
    private Date startDate;

    @Column(nullable = false)
    @NotNull(message = "End date cannot be null")
    @FutureOrPresent(message = "End date must be in the present or future")
    private Date endDate;

    @Column(nullable = false)
    @NotNull(message = "Location cannot be null")
    @Size(min = 1, max = 200, message = "Location must be between 1 and 200 characters")
    private String location;

    @Column(nullable = false)
    @NotNull(message = "Event type cannot be null")
    @Size(min = 1, max = 100, message = "Event type must be between 1 and 100 characters")
    private String eventType;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ApartmentEnt> apartments;
}
