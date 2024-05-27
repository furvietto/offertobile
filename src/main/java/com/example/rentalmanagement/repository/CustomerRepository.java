package com.example.rentalmanagement.repository;

import com.example.rentalmanagement.models.entities.CustomerEnt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<CustomerEnt, Integer> {
}
