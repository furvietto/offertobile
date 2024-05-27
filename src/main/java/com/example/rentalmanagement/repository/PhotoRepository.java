package com.example.rentalmanagement.repository;

import com.example.rentalmanagement.models.entities.PhotoEnt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhotoRepository extends JpaRepository<PhotoEnt, Integer> {

    List<PhotoEnt> findByApartmentId(Integer apartmentId);
}
