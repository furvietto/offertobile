package com.example.rentalmanagement.models.DTO.owner;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

@Data
public class OwnerRequestDTONoId {

    @NotNull(message = "First name cannot be null")
    @Size(min = 1, max = 100, message = "First name must be between 1 and 100 characters")
    private String firstName;

    @NotNull(message = "Last name cannot be null")
    @Size(min = 1, max = 100, message = "Last name must be between 1 and 100 characters")
    private String lastName;

    @NotNull(message = "Date of birth cannot be null")
    @Past(message = "Date of birth must be in the past")
    private Date dateOfBirth;

    @NotNull(message = "Address cannot be null")
    @Size(min = 1, max = 200, message = "Address must be between 1 and 200 characters")
    private String address;

    @NotNull(message = "Email cannot be null")
    @Email(message = "Email should be valid")
    @Size(max = 100, message = "Email must be less than 100 characters")
    private String email;

    @NotNull(message = "Phone cannot be null")
    @Size(min = 10, max = 15, message = "Phone must be between 10 and 15 characters")
    private String phone;

    @NotNull(message = "IBAN cannot be null")
    @Size(min = 15, max = 34, message = "IBAN must be between 15 and 34 characters")
    private String iban;

    @NotNull(message = "Contract date cannot be null")
    private Date contractDate;

    @Size(max = 500, message = "Contract details must be less than 500 characters")
    private String contractDetails;
}
