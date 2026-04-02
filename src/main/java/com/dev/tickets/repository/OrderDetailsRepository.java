package com.dev.tickets.repository;

import com.dev.tickets.model.OrderDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailsRepository extends JpaRepository<OrderDetailEntity, String> {
}
