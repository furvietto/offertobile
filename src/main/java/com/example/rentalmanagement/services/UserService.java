package com.example.rentalmanagement.services;

import com.example.rentalmanagement.exceptions.ResourceNotFoundException;
import com.example.rentalmanagement.models.DTO.user.UserRequestDTONoId;
import com.example.rentalmanagement.models.DTO.user.UserResponseDTO;
import com.example.rentalmanagement.models.DTO.reservation.ReservationResponseDTONoIdNoFk;
import com.example.rentalmanagement.models.entities.UserEnt;
import com.example.rentalmanagement.models.entities.ReservationEnt;
import com.example.rentalmanagement.repository.UserRepository;
import com.example.rentalmanagement.repository.ReservationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;

    public UserService(UserRepository userRepository, ReservationRepository reservationRepository) {
        this.userRepository = userRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<UserResponseDTO> findAll() {
        log.info("Retrieving all customers");
        List<UserEnt> customers = userRepository.findAll();
        return customers.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }


    public UserResponseDTO findById(Integer id) {
        log.info("Retrieving user with ID: {}", id);
        UserEnt customer = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Customer not found with ID: {}", id);
                    return new ResourceNotFoundException("Customer", "id", id);
                });
        return convertToResponseDTO(customer);
    }


    public UserResponseDTO save(UserRequestDTONoId customerDTO) {
        log.info("Creating new user with username: {}", customerDTO.getUsername());
        UserEnt customer = new UserEnt();
        BeanUtils.copyProperties(customerDTO, customer);
        customer = userRepository.save(customer);
        return convertToResponseDTO(customer);
    }


    public UserResponseDTO update(Integer id, UserRequestDTONoId customerDTO) {
        log.info("Updating user with ID: {}", id);
        UserEnt existingCustomer = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Customer not found with ID: {}", id);
                    return new ResourceNotFoundException("Customer", "id", id);
                });
        BeanUtils.copyProperties(customerDTO, existingCustomer, "id");
        userRepository.save(existingCustomer);
        return convertToResponseDTO(existingCustomer);
    }

    public void delete(Integer id) {
        log.info("Deleting user with ID: {}", id);
        if (!userRepository.existsById(id)) {
            log.error("Customer not found with ID: {}", id);
            throw new ResourceNotFoundException("Customer", "id", id);
        }
        userRepository.deleteById(id);
    }


    public List<ReservationResponseDTONoIdNoFk> getReservationsByCustomerId(Integer id) {
        log.info("Retrieving reservations for user with ID: {}", id);
        if (!userRepository.existsById(id)) {
            log.error("Customer not found with ID: {}", id);
            throw new ResourceNotFoundException("Customer", "id", id);
        }
        List<ReservationEnt> reservations = reservationRepository.findByUserId(id);
        return reservations.stream()
                .map(this::convertToReservationResponseDTO)
                .collect(Collectors.toList());
    }

    private UserResponseDTO convertToResponseDTO(UserEnt customer) {
        UserResponseDTO dto = new UserResponseDTO();
        BeanUtils.copyProperties(customer, dto);
        return dto;
    }

    private ReservationResponseDTONoIdNoFk convertToReservationResponseDTO(ReservationEnt reservation) {
        ReservationResponseDTONoIdNoFk dto = new ReservationResponseDTONoIdNoFk();
        BeanUtils.copyProperties(reservation, dto);
        return dto;
    }
}
