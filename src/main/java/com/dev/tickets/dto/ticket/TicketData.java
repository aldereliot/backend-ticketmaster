package com.dev.tickets.dto.ticket;

import com.dev.tickets.model.TicketStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketData {
    private Integer id;
    private String ticketNumber;
    private TicketStatusEnum status;
    private String qrValue;
    private String qrImage;
    private TicketTypeData ticketType;
}
