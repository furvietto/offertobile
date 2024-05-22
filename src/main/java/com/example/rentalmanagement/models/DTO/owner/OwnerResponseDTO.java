package com.example.rentalmanagement.models.DTO.owner;

import lombok.Data;

import java.util.Date;

@Data
public class OwnerResponseDTO {

    private Integer id;

    private String firstName;

    private String lastName;

    private Date dateOfBirth;

    private String address;

    private String email;

    private String phone;

    private String iban;

    private Date contractDate;

    private String contractDetails;
}
