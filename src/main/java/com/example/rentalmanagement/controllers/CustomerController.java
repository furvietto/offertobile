package com.example.rentalmanagement.controllers;

import com.example.rentalmanagement.models.DTO.customer.CustomerRequestDTONoId;
import com.example.rentalmanagement.models.DTO.customer.CustomerResponseDTO;
import com.example.rentalmanagement.models.DTO.reservation.ReservationResponseDTONoIdNoFk;
import com.example.rentalmanagement.services.CustomerService;
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
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * Retrieve all customers.
     * @return List of all customers.
     */
    @Operation(summary = "Retrieve all customers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the customers",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomerResponseDTO.class)) })
    })
    @RequestMapping(
            method = RequestMethod.GET,
            path ="/getAllCustomers",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<CustomerResponseDTO>> getAllCustomers() {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.findAll());
    }

    /**
     * Retrieve a customer by its ID.
     * @param id ID of the customer.
     * @return Customer details.
     */
    @Operation(summary = "Retrieve a customer by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the customer",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomerResponseDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Customer not found",
                    content = @Content)
    })
    @RequestMapping(
            method = RequestMethod.GET,
            path ="/getCustomerById/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CustomerResponseDTO> getCustomer(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.findById(id));
    }

    /**
     * Create a new customer.
     * @param customer DTO containing customer details.
     * @return Created customer details.
     */
    @Operation(summary = "Create a new customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created the customer",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomerResponseDTO.class)) })
    })
    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CustomerResponseDTO> createCustomer(@RequestBody @Valid CustomerRequestDTONoId customer) {
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.save(customer));
    }

    /**
     * Update an existing customer.
     * @param id ID of the customer to update.
     * @param customer DTO containing updated customer details.
     * @return Updated customer details.
     */
    @Operation(summary = "Update an existing customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated the customer",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomerResponseDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Customer not found",
                    content = @Content)
    })
    @RequestMapping(
            method = RequestMethod.PUT,
            path ="/updateCustomer/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CustomerResponseDTO> updateCustomer(@PathVariable Integer id, @RequestBody @Valid CustomerRequestDTONoId customer) {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.update(id, customer));
    }

    /**
     * Delete a customer by its ID.
     * @param id ID of the customer to delete.
     * @return No content.
     */
    @Operation(summary = "Delete a customer by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Deleted the customer",
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
        customerService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * Retrieve reservations by customer ID.
     * @param id ID of the customer.
     * @return List of reservations.
     */
    @Operation(summary = "Retrieve reservations by customer ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the reservations",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ReservationResponseDTONoIdNoFk.class)) }),
            @ApiResponse(responseCode = "404", description = "Customer not found",
                    content = @Content)
    })
    @RequestMapping(
            method = RequestMethod.GET,
            path ="customer/{id}/reservation",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<ReservationResponseDTONoIdNoFk>> getReservationsByCustomerId(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.getReservationsByCustomerId(id));
    }

}
