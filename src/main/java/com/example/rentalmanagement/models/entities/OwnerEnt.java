package com.example.rentalmanagement.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
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
@Table(name = "owner")
public class OwnerEnt {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "OWNER_SEQ_GEN", sequenceName = "owner_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "OWNER_SEQ_GEN")
    private Integer id;

    @Column(nullable = false)
    @NotNull(message = "First name cannot be null")
    @Size(min = 1, max = 100, message = "First name must be between 1 and 100 characters")
    private String firstName;

    @Column(nullable = false)
    @NotNull(message = "Last name cannot be null")
    @Size(min = 1, max = 100, message = "Last name must be between 1 and 100 characters")
    private String lastName;

    @Column(nullable = false)
    @NotNull(message = "Date of birth cannot be null")
    @Past(message = "Date of birth must be in the past")
    private Date dateOfBirth;

    @Column(nullable = false)
    @NotNull(message = "Address cannot be null")
    @Size(min = 1, max = 200, message = "Address must be between 1 and 200 characters")
    private String address;

    @Column(nullable = false)
    @NotNull(message = "Email cannot be null")
    @Email(message = "Email should be valid")
    @Size(max = 100, message = "Email must be less than 100 characters")
    private String email;

    @Column(nullable = false)
    @NotNull(message = "Phone cannot be null")
    @Size(min = 10, max = 15, message = "Phone must be between 10 and 15 characters")
    private String phone;

    @Column(nullable = false)
    @NotNull(message = "IBAN cannot be null")
    @Size(min = 15, max = 34, message = "IBAN must be between 15 and 34 characters")
    private String iban;

    @Column(nullable = false)
    @NotNull(message = "Contract date cannot be null")
    private Date contractDate;

    @Size(max = 500, message = "Contract details must be less than 500 characters")
    private String contractDetails;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ApartmentEnt> apartments;
}
