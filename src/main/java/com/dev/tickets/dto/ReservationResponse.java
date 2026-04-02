package com.dev.tickets.dto;

import com.dev.tickets.dto.event.EventMinifiedResponse;
import com.dev.tickets.model.ReservationStatus;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record ReservationResponse(
        String id,
        LocalDateTime expiresAt,
        EventMinifiedResponse event,
        ReservationStatus status,
        LocalDateTime createdAt,
        List<ReservationItemResponse> items
) {
}
