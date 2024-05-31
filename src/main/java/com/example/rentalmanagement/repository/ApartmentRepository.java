package com.example.rentalmanagement.repository;

import com.example.rentalmanagement.models.entities.ApartmentEnt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApartmentRepository extends JpaRepository<ApartmentEnt, Integer> {
    List<ApartmentEnt> findByEventId(int eventId);

}
