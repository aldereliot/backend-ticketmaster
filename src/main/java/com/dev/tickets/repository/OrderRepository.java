package com.dev.tickets.repository;

import com.dev.tickets.dto.order.OrderMinifiedData;
import com.dev.tickets.model.OrderEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<OrderEntity, String> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT o FROM OrderEntity o WHERE o.id = :id ")
    Optional<OrderEntity> findByIdWithLock(@Param("id") String id);
    @Query("""
            SELECT DISTINCT o FROM OrderEntity o
            JOIN FETCH o.orderDetails od
            WHERE o.user.id = :userId
            """)
    List<OrderEntity> getUserOrders(@Param("userId") Integer userId);
    @Query("""
            SELECT new com.dev.tickets.dto.order.OrderMinifiedData(
                o.id,
                o.total,
                o.status
            )
            FROM OrderEntity o
            WHERE o.id = :id
            ORDER BY o.createdAt ASC
            """)
    Optional<OrderMinifiedData> getOrderById(@Param("id") String orderId);

    @Query("""
            SELECT DISTINCT o FROM OrderEntity o
            JOIN FETCH o.orderDetails od
            JOIN FETCH od.ticketType tt
            JOIN FETCH tt.event
            WHERE o.id = :id
            """)
    Optional<OrderEntity> findByIdWithDetails(@Param("id") String id);
}
