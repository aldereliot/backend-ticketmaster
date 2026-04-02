package com.dev.tickets.mapper;

import com.dev.tickets.dto.event.TicketTypeResponse;
import com.dev.tickets.model.TicketTypeEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TicketStructMapper {
    TicketTypeResponse toResponse(TicketTypeEntity entity);
}
