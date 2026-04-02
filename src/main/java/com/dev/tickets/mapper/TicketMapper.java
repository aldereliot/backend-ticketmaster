package com.dev.tickets.mapper;

import com.dev.tickets.dto.ticket.TicketResponse;
import com.dev.tickets.model.TicketEntity;

public class TicketMapper {

    public static TicketResponse toDto(TicketEntity entity){
        TicketResponse dto = new TicketResponse();
        dto.setId(entity.getId());
        dto.setTicketNumber(entity.getTicketNumber());
        dto.setStatus(entity.getStatus());
        dto.setQrImage(entity.getQrValue());
        return dto;
    }

}
