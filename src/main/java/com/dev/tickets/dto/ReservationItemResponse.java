package com.dev.tickets.dto;

import com.dev.tickets.dto.event.TicketTypeMinifiedResponse;
import lombok.Builder;
import lombok.Getter;

@Builder
public record ReservationItemResponse(
        String id,
        Integer quantity,
        TicketTypeMinifiedResponse ticketType
) {
}
