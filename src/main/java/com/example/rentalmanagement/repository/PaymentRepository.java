package com.example.rentalmanagement.repository;

import com.example.rentalmanagement.models.entities.PaymentEnt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<PaymentEnt, Integer> {

    List<PaymentEnt> findByReservationId(int reservationId);
}
