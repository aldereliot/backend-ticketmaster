package com.dev.tickets.dto.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TicketTypeMinifiedResponse {
    private Integer id;
    private String name;
    private BigDecimal price;
    private Integer totalAvailable;
}
