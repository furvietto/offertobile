package com.example.rentalmanagement.repository;

import com.example.rentalmanagement.models.entities.UserEnt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEnt, Integer> {

    Optional<UserEnt> findByUsername(String username);
}
