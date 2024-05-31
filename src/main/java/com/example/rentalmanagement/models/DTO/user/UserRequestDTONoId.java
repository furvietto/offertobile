package com.example.rentalmanagement.models.DTO.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTONoId {

    @NotNull(message = "firstname should not be null or empty")
    @Size(min = 1, max = 100,message = "First name must be between 1 and 100 character")
    private String firstName;

    @NotNull(message = "lastname should not be null or empty")
    @Size(min = 1, max = 100, message = "Last name must be between 1 and 100 characters")
    private String lastName;

    @NotNull(message = "date of birth should not be null or empty")
    @Past(message = "Date of birth must be in the past")
    private Date dateOfBirth;

    @NotNull(message = "address should not be null or empty")
    @Size(min = 1, max = 200,message = "Address must be between 1 and 200 characters")
    private String address;

    @NotNull(message = "email should not be null or empty")
    @Email(message = "Email should be valid")
    @Size(max = 100,message = "Email must be less than 100 characters")
    private String email;

    @NotNull(message = "phone should not be null or empty")
    @Size(min = 10, max = 15,message = "Phone must be between 10 and 15 characters")
    private String phone;

    @NotNull(message = "username should not be null or empty")
    @Size(min = 5, max = 50, message ="Username must be between 5 and 50 characters")
    private String username;

    @NotNull(message = "Password must be at least 8 characters")
    @Size(min = 8)
    private String password;
}
