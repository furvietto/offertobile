package com.example.rentalmanagement.controllers;

import com.example.rentalmanagement.models.DTO.user.UserRequestDTONoId;
import com.example.rentalmanagement.models.DTO.user.UserResponseDTO;
import com.example.rentalmanagement.models.DTO.reservation.ReservationResponseDTONoIdNoFk;
import com.example.rentalmanagement.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/v1/customers")
@Tag(name = "Customer Management", description = "Operations pertaining to customers in the Rental Management System")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Retrieve all customers.
     * @return List of all customers.
     */
    @Operation(summary = "Retrieve all customers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the customers",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDTO.class)) })
    })
    @RequestMapping(
            method = RequestMethod.GET,
            path ="/getAllCustomers",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<UserResponseDTO>> getAllCustomers() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
    }

    /**
     * Retrieve a user by its ID.
     * @param id ID of the user.
     * @return Customer details.
     */
    @Operation(summary = "Retrieve a user by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the user",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Customer not found",
                    content = @Content)
    })
    @RequestMapping(
            method = RequestMethod.GET,
            path ="/getCustomerById/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<UserResponseDTO> getCustomer(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findById(id));
    }

    /**
     * Create a new user.
     * @param customer DTO containing user details.
     * @return Created user details.
     */
    @Operation(summary = "Create a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created the user",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDTO.class)) })
    })
    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<UserResponseDTO> createCustomer(@RequestBody @Valid UserRequestDTONoId customer) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(customer));
    }

    /**
     * Update an existing user.
     * @param id ID of the user to update.
     * @param customer DTO containing updated user details.
     * @return Updated user details.
     */
    @Operation(summary = "Update an existing user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated the user",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Customer not found",
                    content = @Content)
    })
    @RequestMapping(
            method = RequestMethod.PUT,
            path ="/updateCustomer/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<UserResponseDTO> updateCustomer(@PathVariable Integer id, @RequestBody @Valid UserRequestDTONoId customer) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.update(id, customer));
    }

    /**
     * Delete a user by its ID.
     * @param id ID of the user to delete.
     * @return No content.
     */
    @Operation(summary = "Delete a user by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Deleted the user",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Customer not found",
                    content = @Content)
    })
    @RequestMapping(
            method = RequestMethod.DELETE,
            path ="/deleteCustomer/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Void> deleteCustomer(@PathVariable Integer id) {
        userService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * Retrieve reservations by user ID.
     * @param id ID of the user.
     * @return List of reservations.
     */
    @Operation(summary = "Retrieve reservations by user ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the reservations",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ReservationResponseDTONoIdNoFk.class)) }),
            @ApiResponse(responseCode = "404", description = "Customer not found",
                    content = @Content)
    })
    @RequestMapping(
            method = RequestMethod.GET,
            path ="user/{id}/reservation",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<ReservationResponseDTONoIdNoFk>> getReservationsByCustomerId(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getReservationsByCustomerId(id));
    }

}
