package com.dev.tickets.mapper;

import com.dev.tickets.dto.event.EventResponse;
import com.dev.tickets.model.EventEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {
        UserStructMapper.class,
        TicketStructMapper.class
})
public interface EventStructMapper {
    EventResponse toResponse(EventEntity event);
}
