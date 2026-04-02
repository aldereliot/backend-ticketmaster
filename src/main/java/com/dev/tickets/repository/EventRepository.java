package com.dev.tickets.repository;

import com.dev.tickets.dto.event.EventCardResponse;
import com.dev.tickets.model.EventEntity;
import com.dev.tickets.model.EventStatusEnum;
import com.dev.tickets.model.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<EventEntity, Integer>, JpaSpecificationExecutor<EventEntity> {

    @Query("""
            SELECT DISTINCT e FROM EventEntity e
            LEFT JOIN FETCH e.ticketTypes t
            WHERE e.id = :id
            AND (t is NULL OR t.isDeleted = false)
            """)
    Optional<EventEntity> findByIdWithActiveTickets(@Param("id") Integer id);
    @EntityGraph(attributePaths = {"ticketTypes", "category"})
    Optional<EventEntity> findBySlug(String slug);
    List<EventEntity> findByOrganizer(UserEntity user);
    List<EventEntity> findByStatus(EventStatusEnum status);
    List<EventEntity> findByStatusAndNameContaining(EventStatusEnum status, String name);
    @Query("""
        SELECT new com.dev.tickets.dto.event.EventCardResponse(
            e.id,
            e.name,
            e.slug,
            e.image,
            e.startDate,
            e.location
        )
        FROM EventEntity e
        WHERE e.status = :status
        AND e.startDate >= :now
        AND (
            LOWER(e.name) LIKE LOWER(CONCAT('%', :query, '%'))
            OR LOWER(e.description) LIKE LOWER(CONCAT('%', :query, '%'))
        )
    """)
    List<EventCardResponse> searchPublishedEvents( @Param("status") EventStatusEnum status, @Param("now") LocalDateTime now, @Param("query") String query);

    @Query("""
            SELECT DISTINCT e FROM EventEntity e
            LEFT JOIN FETCH e.ticketTypes t
            WHERE e.status = :status
            AND e.slug = :slug
            AND (t is NULL OR t.isDeleted = false)
            """)
    Optional<EventEntity> findByStatusAndSlug(EventStatusEnum status, String slug);

    @Query("""
    SELECT new com.dev.tickets.dto.event.EventCardResponse(
        e.id,
        e.name,
        e.slug,
        e.image,
        e.startDate,
        e.location
    )
    FROM EventEntity e
    WHERE e.status = :status
    AND e.startDate >= :now
    AND EXISTS (
        SELECT t.id
        FROM TicketTypeEntity t
        WHERE t.event = e
        AND t.totalAvailable > 0
        AND t.isDeleted = false
    )
    """)
    List<EventCardResponse> findHeroEvents(@Param("status") EventStatusEnum status, @Param("now") LocalDateTime now, Pageable pageable);

    @Query("""
    SELECT new com.dev.tickets.dto.event.EventCardResponse(
        e.id,
        e.name,
        e.slug,
        e.image,
        e.startDate,
        e.location
    )
    FROM EventEntity e
    WHERE e.status = :status
    AND e.category.name = :categoryName
    AND e.startDate >= :now
    AND EXISTS (
        SELECT t.id
        FROM TicketTypeEntity t
        WHERE t.event = e
        AND t.totalAvailable > 0
    )
    """
    )
    List<EventCardResponse> findByCategoryName(@Param("status") EventStatusEnum status, @Param("categoryName") String categoryName, @Param("now") LocalDateTime now, Pageable pageable);
    @Query("""
            SELECT e FROM EventEntity e
            WHERE e.startDate < :time
            AND e.status <> :status
            """)
    List<EventEntity> findExpiredEventsAndActive(
            @Param("time") LocalDateTime now,
            @Param("status") EventStatusEnum status
    );
}
