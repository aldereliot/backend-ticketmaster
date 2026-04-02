package com.dev.tickets.mapper;

import com.dev.tickets.dto.validation.ValidationResponse;
import com.dev.tickets.model.TicketValidationEntity;

public class ValidationMapper {

    public static ValidationResponse toDto(TicketValidationEntity entity){
        ValidationResponse dto = new ValidationResponse();
        dto.setId(entity.getId());
        dto.setStatus(entity.getStatus());
        dto.setValidationMethod(entity.getValidationMethod());
        dto.setTicket( TicketMapper.toDto(entity.getTicket()) );
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;
    }

}
