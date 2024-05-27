package com.example.rentalmanagement.services;

import com.example.rentalmanagement.exceptions.ResourceNotFoundException;
import com.example.rentalmanagement.models.DTO.payment.PaymentRequestDTONoId;
import com.example.rentalmanagement.models.DTO.payment.PaymentResponseDTO;
import com.example.rentalmanagement.models.DTO.payment.PaymentResponseDTONoIdNoFk;
import com.example.rentalmanagement.models.entities.PaymentEnt;
import com.example.rentalmanagement.models.entities.ReservationEnt;
import com.example.rentalmanagement.repository.PaymentRepository;
import com.example.rentalmanagement.repository.ReservationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final ReservationRepository reservationRepository;

    public PaymentService(PaymentRepository paymentRepository, ReservationRepository reservationRepository) {
        this.paymentRepository = paymentRepository;
        this.reservationRepository = reservationRepository;
    }


    public List<PaymentResponseDTO> findAll() {
        log.info("Retrieving all payments");
        List<PaymentEnt> payments = paymentRepository.findAll();
        return payments.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public PaymentResponseDTO findById(Integer id) {
        log.info("Retrieving payment with ID: {}", id);
        PaymentEnt payment = paymentRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Payment not found with ID: {}", id);
                    return new ResourceNotFoundException("Payment", "id", id);
                });
        return convertToResponseDTO(payment);
    }


    public PaymentResponseDTO save(PaymentRequestDTONoId paymentDTO) {
        log.info("Creating new payment with amount: {}", paymentDTO.getAmount());
        PaymentEnt payment = new PaymentEnt();
        BeanUtils.copyProperties(paymentDTO, payment);
        setReferences(payment, paymentDTO);
        payment = paymentRepository.save(payment);
        return convertToResponseDTO(payment);
    }

    public PaymentResponseDTO update(Integer id, PaymentRequestDTONoId paymentDTO) {
        log.info("Updating payment with ID: {}", id);
        PaymentEnt existingPayment = paymentRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Payment not found with ID: {}", id);
                    return new ResourceNotFoundException("Payment", "id", id);
                });
        BeanUtils.copyProperties(paymentDTO, existingPayment, "id");
        setReferences(existingPayment, paymentDTO);
        paymentRepository.save(existingPayment);
        return convertToResponseDTO(existingPayment);
    }

    public void delete(Integer id) {
        log.info("Deleting payment with ID: {}", id);
        if (!paymentRepository.existsById(id)) {
            log.error("Payment not found with ID: {}", id);
            throw new ResourceNotFoundException("Payment", "id", id);
        }
        paymentRepository.deleteById(id);
    }

    public PaymentResponseDTONoIdNoFk getReservationByPaymentId(Integer id) {
        log.info("Retrieving reservation for payment with ID: {}", id);
        PaymentEnt payment = paymentRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Payment not found with ID: {}", id);
                    return new ResourceNotFoundException("Payment", "id", id);
                });
        return convertToReservationResponseDTO(payment);
    }

    private void setReferences(PaymentEnt payment, PaymentRequestDTONoId paymentDTO) {
        ReservationEnt reservation = reservationRepository.findById(paymentDTO.getReservationId())
                .orElseThrow(() -> {
                    log.error("Reservation not found with ID: {}", paymentDTO.getReservationId());
                    return new ResourceNotFoundException("Reservation", "id", paymentDTO.getReservationId());
                });
        payment.setReservation(reservation);
    }

    private PaymentResponseDTO convertToResponseDTO(PaymentEnt payment) {
        PaymentResponseDTO dto = new PaymentResponseDTO();
        BeanUtils.copyProperties(payment, dto);
        dto.setReservationId(payment.getReservation().getId());
        return dto;
    }

    private PaymentResponseDTONoIdNoFk convertToReservationResponseDTO(PaymentEnt payment) {
        PaymentResponseDTONoIdNoFk dto = new PaymentResponseDTONoIdNoFk();
        BeanUtils.copyProperties(payment, dto);
        return dto;
    }
}
