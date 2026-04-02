package com.dev.tickets.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailData {
    private String id;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal subtotal;
    private Integer ticketsCount;
    private TicketTypeData ticketType;
}
