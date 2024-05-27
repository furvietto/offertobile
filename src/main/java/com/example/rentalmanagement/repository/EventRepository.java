package com.example.rentalmanagement.repository;

import com.example.rentalmanagement.models.entities.EventEnt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<EventEnt, Integer> {
}
