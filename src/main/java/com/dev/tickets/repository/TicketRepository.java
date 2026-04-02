package com.dev.tickets.repository;

import com.dev.tickets.model.TicketEntity;
import com.dev.tickets.model.TicketStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<TicketEntity, Integer> {
    Optional<TicketEntity> findByTicketNumberAndStatus(String ticketNumber, TicketStatusEnum status);
    Optional<TicketEntity> findByTicketNumber(String ticketNumber);
    Optional<TicketEntity> findByQrValue(String qrValue);
    List<TicketEntity> findByOrderDetailId(String orderDetailId);
    @Query("""
            SELECT t FROM TicketEntity t
            JOIN FETCH t.ticketType tt
            JOIN FETCH tt.event e
            JOIN t.orderDetail od
            JOIN od.order o
            WHERE o.user.id = :userId
            ORDER BY e.startDate ASC
            """)
    List<TicketEntity> getUserTickets(@Param("userId") Integer userId);


}
