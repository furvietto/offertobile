package com.example.rentalmanagement.controllers;

import com.example.rentalmanagement.models.DTO.customer.CustomerRequestDTONoId;
import com.example.rentalmanagement.models.DTO.customer.CustomerResponseDTO;
import com.example.rentalmanagement.models.DTO.reservation.ReservationResponseDTONoIdNoFk;
import com.example.rentalmanagement.services.CustomerService;
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
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * Retrieve all customers.
     * @return List of all customers.
     */
    @RequestMapping(
            method = RequestMethod.GET,
            path ="/getAllCustomers",
            consumes = MediaType.APPLICATION_JSON_VALUE,
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
    @RequestMapping(
            method = RequestMethod.GET,
            path ="/getCustomerById/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CustomerResponseDTO> getCustomer(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.findById(id));
    }

    /**
     * Create a new customer.
     * @param customer DTO containing customer details.
     * @return Created customer details.
     */
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
    @RequestMapping(
            method = RequestMethod.PUT,
            path ="/updateCustomer/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CustomerResponseDTO> updateCustomer(@PathVariable int id, @RequestBody @Valid CustomerRequestDTONoId customer) {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.update(id, customer));
    }

    /**
     * Delete a customer by its ID.
     * @param id ID of the customer to delete.
     * @return No content.
     */
    @RequestMapping(
            method = RequestMethod.DELETE,
            path ="/deleteCustomer/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Void> deleteCustomer(@PathVariable int id) {
        customerService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * Retrieve reservations by customer ID.
     * @param id ID of the customer.
     * @return List of reservations.
     */
    @RequestMapping(
            method = RequestMethod.GET,
            path ="customer/{id}/reservation",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<ReservationResponseDTONoIdNoFk>> getReservationsByCustomerId(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.getReservationsByCustomerId(id));
    }

}
