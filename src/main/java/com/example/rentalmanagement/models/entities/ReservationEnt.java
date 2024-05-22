package com.example.rentalmanagement.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
@Table(name = "reservation")
public class ReservationEnt {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "RESERVATION_SEQ_GEN", sequenceName = "reservation_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RESERVATION_SEQ_GEN")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "apartment_id", nullable = false)
    @NotNull(message = "Apartment ID cannot be null")
    private ApartmentEnt apartment;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    @NotNull(message = "Customer ID cannot be null")
    private CustomerEnt customer;

    @Column(nullable = false)
    @NotNull(message = "Start date cannot be null")
    @FutureOrPresent(message = "Start date must be in the present or future")
    private Date startDate;

    @Column(nullable = false)
    @NotNull(message = "End date cannot be null")
    @FutureOrPresent(message = "End date must be in the present or future")
    private Date endDate;

    @Column(nullable = false)
    @NotNull(message = "Total payment cannot be null")
    @Positive(message = "Total payment must be positive")
    private Double totalPayment;

    @Column(nullable = false)
    @NotNull(message = "Status cannot be null")
    @Size(min = 1, max = 50, message = "Status must be between 1 and 50 characters")
    private String status;

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PaymentEnt> payments;
}
