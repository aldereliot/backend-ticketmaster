package com.dev.tickets.dto.event;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TicketTypeRequest {
    private Integer id;
    private String name;
    private BigDecimal price;
    private Integer totalAvailable;
    private TicketAction action;
}
