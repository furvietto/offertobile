package com.example.rentalmanagement.repository;

import com.example.rentalmanagement.models.entities.RoleEnt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleEnt, Integer> {
}
