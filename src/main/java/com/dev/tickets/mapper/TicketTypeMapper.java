package com.dev.tickets.mapper;

import com.dev.tickets.dto.event.TicketTypeMinifiedResponse;
import com.dev.tickets.dto.event.TicketTypeResponse;
import com.dev.tickets.model.TicketTypeEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TicketTypeMapper {

    public static TicketTypeMinifiedResponse toMinifiedDto(TicketTypeEntity entity){
        TicketTypeMinifiedResponse dto = new TicketTypeMinifiedResponse();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setPrice( entity.getPrice() );
        dto.setTotalAvailable( entity.getAvailable() );
        return dto;
    }

    public static TicketTypeResponse toDto(TicketTypeEntity entity){
        TicketTypeResponse dto = new TicketTypeResponse();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setPrice( entity.getPrice() );
        dto.setTotalAvailable( entity.getAvailable() );
        dto.setDeleted(entity.isDeleted());
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;
    }

    public static List<TicketTypeResponse> listToDto(List<TicketTypeEntity> tickets){
        return tickets.stream()
                .map( TicketTypeMapper::toDto )
                .toList();
    }

}
