package com.example.rentalmanagement.repository;

import com.example.rentalmanagement.models.entities.OwnerEnt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerRepository extends JpaRepository<OwnerEnt, Integer> {
}