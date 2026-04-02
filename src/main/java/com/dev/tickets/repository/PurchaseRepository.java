package com.dev.tickets.repository;

import com.dev.tickets.model.PurchaseEntity;
import com.dev.tickets.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<PurchaseEntity, Integer> {
    List<PurchaseEntity> findByBuyer(UserEntity buyer);
}
