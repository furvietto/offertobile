package com.example.rentalmanagement.services;

import com.example.rentalmanagement.exceptions.ResourceNotFoundException;
import com.example.rentalmanagement.models.DTO.customer.CustomerRequestDTONoId;
import com.example.rentalmanagement.models.DTO.customer.CustomerResponseDTO;
import com.example.rentalmanagement.models.DTO.reservation.ReservationResponseDTONoIdNoFk;
import com.example.rentalmanagement.models.entities.CustomerEnt;
import com.example.rentalmanagement.models.entities.ReservationEnt;
import com.example.rentalmanagement.repository.CustomerRepository;
import com.example.rentalmanagement.repository.ReservationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class  CustomerService {

    private final CustomerRepository customerRepository;
    private final ReservationRepository reservationRepository;

    public CustomerService(CustomerRepository customerRepository, ReservationRepository reservationRepository) {
        this.customerRepository = customerRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<CustomerResponseDTO> findAll() {
        log.info("Retrieving all customers");
        List<CustomerEnt> customers = customerRepository.findAll();
        return customers.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }


    public CustomerResponseDTO findById(int id) {
        log.info("Retrieving customer with ID: {}", id);
        CustomerEnt customer = customerRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Customer not found with ID: {}", id);
                    return new ResourceNotFoundException("Customer", "id", id);
                });
        return convertToResponseDTO(customer);
    }


    public CustomerResponseDTO save(CustomerRequestDTONoId customerDTO) {
        log.info("Creating new customer with username: {}", customerDTO.getUsername());
        CustomerEnt customer = new CustomerEnt();
        BeanUtils.copyProperties(customerDTO, customer);
        customer = customerRepository.save(customer);
        return convertToResponseDTO(customer);
    }


    public CustomerResponseDTO update(int id, CustomerRequestDTONoId customerDTO) {
        log.info("Updating customer with ID: {}", id);
        CustomerEnt existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Customer not found with ID: {}", id);
                    return new ResourceNotFoundException("Customer", "id", id);
                });
        BeanUtils.copyProperties(customerDTO, existingCustomer, "id");
        customerRepository.save(existingCustomer);
        return convertToResponseDTO(existingCustomer);
    }

    public void delete(int id) {
        log.info("Deleting customer with ID: {}", id);
        if (!customerRepository.existsById(id)) {
            log.error("Customer not found with ID: {}", id);
            throw new ResourceNotFoundException("Customer", "id", id);
        }
        customerRepository.deleteById(id);
    }


    public List<ReservationResponseDTONoIdNoFk> getReservationsByCustomerId(Integer id) {
        log.info("Retrieving reservations for customer with ID: {}", id);
        if (!customerRepository.existsById(id)) {
            log.error("Customer not found with ID: {}", id);
            throw new ResourceNotFoundException("Customer", "id", id);
        }
        List<ReservationEnt> reservations = reservationRepository.findByCustomerId(id);
        return reservations.stream()
                .map(this::convertToReservationResponseDTO)
                .collect(Collectors.toList());
    }

    private CustomerResponseDTO convertToResponseDTO(CustomerEnt customer) {
        CustomerResponseDTO dto = new CustomerResponseDTO();
        BeanUtils.copyProperties(customer, dto);
        return dto;
    }

    private ReservationResponseDTONoIdNoFk convertToReservationResponseDTO(ReservationEnt reservation) {
        ReservationResponseDTONoIdNoFk dto = new ReservationResponseDTONoIdNoFk();
        BeanUtils.copyProperties(reservation, dto);
        return dto;
    }
}
