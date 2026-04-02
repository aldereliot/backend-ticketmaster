package com.dev.tickets.dto.ticket;

import com.dev.tickets.dto.event.TicketTypeResponse;
import com.dev.tickets.model.TicketStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TicketResponse {
    private Integer id;
    private String ticketNumber;
    private TicketStatusEnum status;
    private String qrImage;
}
