package com.dev.tickets.mapper;

import com.dev.tickets.dto.ReservationItemResponse;
import com.dev.tickets.dto.ReservationResponse;
import com.dev.tickets.model.ReservationEntity;

import java.util.List;

public class ReservationMapper {

    public static ReservationResponse toDto(ReservationEntity entity){

        List<ReservationItemResponse> items = entity.getItems().stream()
                .map( item -> {
                    return ReservationItemResponse.builder()
                            .id(item.getId())
                            .quantity(item.getQuantity())
                            .ticketType( TicketTypeMapper.toMinifiedDto(item.getTicketType()) )
                            .build();
                }).toList();

        return ReservationResponse.builder()
                .id(entity.getId())
                .expiresAt(entity.getExpiresAt())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .event( EventMapper.toMinifiedDto(entity.getEvent()) )
                .items( items )
                .build();
    }

}
