package com.dev.tickets.repository;

import com.dev.tickets.model.ReservationItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationItemRepository extends JpaRepository<ReservationItem, String> {
}
