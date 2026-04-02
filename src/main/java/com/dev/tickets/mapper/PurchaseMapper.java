package com.dev.tickets.mapper;

import com.dev.tickets.dto.purchase.PurchaseResponse;
import com.dev.tickets.model.EventEntity;
import com.dev.tickets.model.PurchaseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PurchaseMapper {

    public static PurchaseResponse toDto(PurchaseEntity entity){
        EventEntity eventEntity = entity.getTickets().getFirst().getTicketType().getEvent();

        PurchaseResponse dto = new PurchaseResponse();
        dto.setId(entity.getId());
        dto.setTotal( entity.getTotal() );
        dto.setEvent( EventMapper.toDto(eventEntity) );
        dto.setBuyer( UserMapper.toDto(entity.getBuyer()) );
        dto.setCreatedAt( entity.getCreatedAt() );
        dto.setTickets( entity.getTickets().stream().map( TicketMapper::toDto ).toList() );
        return dto;
    }

    public static List<PurchaseResponse> listToDto(List<PurchaseEntity> purchases){
        return purchases.stream().map( PurchaseMapper::toDto ).toList();
    }

}
