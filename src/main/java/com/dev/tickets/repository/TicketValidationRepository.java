package com.dev.tickets.repository;

import com.dev.tickets.model.TicketValidationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketValidationRepository extends JpaRepository<TicketValidationEntity, Integer> {
}
