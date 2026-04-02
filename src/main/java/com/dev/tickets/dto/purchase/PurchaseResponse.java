package com.dev.tickets.dto.purchase;

import com.dev.tickets.dto.event.EventResponse;
import com.dev.tickets.dto.ticket.TicketResponse;
import com.dev.tickets.dto.user.UserResponse;
import com.dev.tickets.model.EventEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseResponse {
    private Integer id;
    private LocalDateTime createdAt;
    private BigDecimal total;
    private EventResponse event;
    private UserResponse buyer;
    private List<TicketResponse> tickets = new ArrayList<>();
}
