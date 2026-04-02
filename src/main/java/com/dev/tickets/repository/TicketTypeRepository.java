package com.dev.tickets.repository;

import com.dev.tickets.model.TicketTypeEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TicketTypeRepository extends JpaRepository<TicketTypeEntity, Integer> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT tt from TicketTypeEntity tt where tt.id = :id ")
    Optional<TicketTypeEntity> findByIdWithLock(@Param("id") Integer id);
    @Modifying
    @Query("""
            UPDATE TicketTypeEntity t
            SET t.reserved = t.reserved + :qty
            WHERE t.id = :id
            AND ( t.totalAvailable - t.reserved - t.sold ) >= :qty
            """)
    int reserveIfAvailable(@Param("id") Integer id, @Param("qty") int qty);

    @Modifying
    @Query("""
            UPDATE TicketTypeEntity t
            SET t.reserved = t.reserved - :qty
            WHERE t.id = :id
            AND t.reserved >= :qty
            """)
    int releaseReserved(@Param("id") Integer id, @Param("qty") int qty);
}
