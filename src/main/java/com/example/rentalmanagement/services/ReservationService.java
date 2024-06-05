package com.example.rentalmanagement.services;

import com.example.rentalmanagement.exceptions.ResourceNotFoundException;
import com.example.rentalmanagement.models.DTO.payment.PaymentResponseDTONoIdNoFk;
import com.example.rentalmanagement.models.DTO.reservation.ReservationRequestDTONoId;
import com.example.rentalmanagement.models.DTO.reservation.ReservationResponseDTO;
import com.example.rentalmanagement.models.entities.ApartmentEnt;
import com.example.rentalmanagement.models.entities.UserEnt;
import com.example.rentalmanagement.models.entities.PaymentEnt;
import com.example.rentalmanagement.models.entities.ReservationEnt;
import com.example.rentalmanagement.repository.ApartmentRepository;
import com.example.rentalmanagement.repository.UserRepository;
import com.example.rentalmanagement.repository.PaymentRepository;
import com.example.rentalmanagement.repository.ReservationRepository;
import com.example.rentalmanagement.services.email.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ApartmentRepository apartmentRepository;
    private final UserRepository userRepository;
    private final PaymentRepository paymentRepository;
    private final EmailService emailService;

    public ReservationService(ReservationRepository reservationRepository, ApartmentRepository apartmentRepository, UserRepository userRepository, PaymentRepository paymentRepository, EmailService emailService) {
        this.reservationRepository = reservationRepository;
        this.apartmentRepository = apartmentRepository;
        this.userRepository = userRepository;
        this.paymentRepository = paymentRepository;
        this.emailService = emailService;
    }

    public List<ReservationResponseDTO> findAll() {
        log.info("Retrieving all reservations");
        List<ReservationEnt> reservations = reservationRepository.findAll();
        return reservations.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public ReservationResponseDTO findById(Integer id) {
        log.info("Retrieving reservation with ID: {}", id);
        ReservationEnt reservation = reservationRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Reservation not found with ID: {}", id);
                    return new ResourceNotFoundException("Reservation", "id", id);
                });
        return convertToResponseDTO(reservation);
    }

    public ReservationResponseDTO save(ReservationRequestDTONoId reservationDTO) {
        log.info("Creating new reservation");
        ReservationEnt reservation = new ReservationEnt();
        BeanUtils.copyProperties(reservationDTO, reservation);
        setReferences(reservation, reservationDTO);
        reservation = reservationRepository.save(reservation);

        // Invia email all'utente
        emailService.sendSimpleEmail(reservation.getUser().getEmail(), "Reservation Confirmation", "Your reservation has been successfully created.");

        // Invia email al proprietario
        emailService.sendSimpleEmail(reservation.getApartment().getUser().getEmail(), "New Reservation", "A new reservation has been created for your apartment.");

        return convertToResponseDTO(reservation);
    }

    public ReservationResponseDTO update(Integer id, ReservationRequestDTONoId reservationDTO) {
        log.info("Updating reservation with ID: {}", id);
        ReservationEnt existingReservation = reservationRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Reservation not found with ID: {}", id);
                    return new ResourceNotFoundException("Reservation", "id", id);
                });
        BeanUtils.copyProperties(reservationDTO, existingReservation, "id");
        setReferences(existingReservation, reservationDTO);
        reservationRepository.save(existingReservation);
        return convertToResponseDTO(existingReservation);
    }

    public void delete(int id) {
        log.info("Deleting reservation with ID: {}", id);
        if (!reservationRepository.existsById(id)) {
            log.error("Reservation not found with ID: {}", id);
            throw new ResourceNotFoundException("Reservation", "id", id);
        }
        reservationRepository.deleteById(id);
    }

    public List<PaymentResponseDTONoIdNoFk> getPaymentsByReservationId(Integer id) {
        log.info("Retrieving payments for reservation with ID: {}", id);
        if (!reservationRepository.existsById(id)) {
            log.error("Reservation not found with ID: {}", id);
            throw new ResourceNotFoundException("Reservation", "id", id);
        }
        List<PaymentEnt> payments = paymentRepository.findByReservationId(id);
        return payments.stream()
                .map(this::convertToPaymentResponseDTO)
                .collect(Collectors.toList());
    }

    private void setReferences(ReservationEnt reservation, ReservationRequestDTONoId reservationDTO) {
        ApartmentEnt apartment = apartmentRepository.findById(reservationDTO.getApartmentId())
                .orElseThrow(() -> {
                    log.error("Apartment not found with ID: {}", reservationDTO.getApartmentId());
                    return new ResourceNotFoundException("Apartment", "id", reservationDTO.getApartmentId());
                });
        UserEnt customer = userRepository.findById(reservationDTO.getCustomerId())
                .orElseThrow(() -> {
                    log.error("Customer not found with ID: {}", reservationDTO.getCustomerId());
                    return new ResourceNotFoundException("Customer", "id", reservationDTO.getCustomerId());
                });
        reservation.setApartment(apartment);
        reservation.setUser(customer);
    }

    private ReservationResponseDTO convertToResponseDTO(ReservationEnt reservation) {
        ReservationResponseDTO dto = new ReservationResponseDTO();
        BeanUtils.copyProperties(reservation, dto);
        dto.setApartmentId(reservation.getApartment().getId());
        dto.setCustomerId(reservation.getUser().getId());
        return dto;
    }

    private PaymentResponseDTONoIdNoFk convertToPaymentResponseDTO(PaymentEnt payment) {
        PaymentResponseDTONoIdNoFk dto = new PaymentResponseDTONoIdNoFk();
        dto.setPaymentDate(payment.getPaymentDate());
        dto.setAmount(payment.getAmount());
        dto.setPaymentMethod(payment.getPaymentMethod());
        dto.setPaymentStatus(payment.getPaymentStatus());
        return dto;
    }
}