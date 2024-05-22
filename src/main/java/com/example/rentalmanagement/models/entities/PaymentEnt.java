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

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "payment")
public class PaymentEnt {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "PAYMENT_SEQ_GEN", sequenceName = "payment_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PAYMENT_SEQ_GEN")
    private Integer id;

    @Column(nullable = false)
    @NotNull(message = "Payment date cannot be null")
    @FutureOrPresent(message = "Payment date must be in the present or future")
    private Date paymentDate;

    @Column(nullable = false)
    @NotNull(message = "Amount cannot be null")
    @Positive(message = "Amount must be positive")
    private Double amount;

    @Column(nullable = false)
    @NotNull(message = "Payment method cannot be null")
    @Size(min = 1, max = 50, message = "Payment method must be between 1 and 50 characters")
    private String paymentMethod;

    @Column(nullable = false)
    @NotNull(message = "Payment status cannot be null")
    private Boolean paymentStatus;

    @ManyToOne
    @JoinColumn(name = "reservation_id", nullable = false)
    @NotNull(message = "Reservation ID cannot be null")
    private ReservationEnt reservation;
}
