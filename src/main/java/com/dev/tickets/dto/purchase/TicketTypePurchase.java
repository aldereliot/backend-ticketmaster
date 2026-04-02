package com.dev.tickets.dto.purchase;

import com.dev.tickets.model.TicketTypeEntity;

public record TicketTypePurchase(
        TicketTypeEntity ticketType,
        Integer quantity
){}
