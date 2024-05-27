package com.example.rentalmanagement.repository;

import com.example.rentalmanagement.models.entities.PaymentEnt;
import com.example.rentalmanagement.models.entities.ReservationEnt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<ReservationEnt, Integer> {

    List<ReservationEnt> findByCustomerId(Integer customerId);

    List<ReservationEnt> findByApartmentId(Integer apartmentId);
}