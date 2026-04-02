package com.dev.tickets.mapper;

import com.dev.tickets.dto.event.EventMinifiedResponse;
import com.dev.tickets.dto.event.EventRequest;
import com.dev.tickets.dto.event.EventResponse;
import com.dev.tickets.dto.event.TicketTypeRequest;
import com.dev.tickets.libs.Utils;
import com.dev.tickets.model.CategoryEntity;
import com.dev.tickets.model.EventEntity;
import com.dev.tickets.model.TicketStatusEnum;
import com.dev.tickets.model.TicketTypeEntity;
import com.dev.tickets.repository.TicketTypeRepository;
import jdk.jfr.EventType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class EventMapper {


    public static EventEntity toEntity(EventRequest request, CategoryEntity category){
        EventEntity event = new EventEntity();
        EventMapper.updateEntity(event, request, category);
        return event;
    }

    public static void updateEntity(EventEntity event, EventRequest request, CategoryEntity category){
        event.setName(request.getName());
        event.setImage(request.getImage());
        event.setSlug( Utils.slugify(request.getName()) ) ;
        event.setDescription(request.getDescription());
        event.setDetalle(request.getDetalle());
        event.setLocation(request.getLocation());
        event.setStartDate(request.getStartDate());
        event.setStatus(request.getStatus());
        event.setCategory(category);
    }

    public static EventResponse toDto(EventEntity entity){
        EventResponse dto = new EventResponse();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSlug(entity.getSlug());
        dto.setImage(entity.getImage());
        dto.setStartDate(entity.getStartDate());
        dto.setLocation(entity.getLocation());
        dto.setDescription(entity.getDescription());
        dto.setDetalle(entity.getDetalle());
        dto.setStatus(entity.getStatus());
        dto.setVenue(entity.getVenue());
        dto.setCategory( CategoryMapper.toResponse(entity.getCategory() ) );
        dto.setOrganizer( UserMapper.toDto(entity.getOrganizer() ) );
        dto.setTicketTypes(
                entity.getTicketTypes().stream()
                        .filter( ticket -> !ticket.isDeleted() )
                        .map( TicketTypeMapper::toDto )
                        .toList());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }
    public static List<EventResponse> listToDto(List<EventEntity> events){
        return events.stream()
                .map( EventMapper::toDto )
                .toList();
    }

    public static EventMinifiedResponse toMinifiedDto(EventEntity entity){
        EventMinifiedResponse dto = new EventMinifiedResponse();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSlug(entity.getSlug());
        dto.setImage(entity.getImage());
        dto.setStartDate(entity.getStartDate());
        dto.setLocation(entity.getLocation());
        dto.setDescription(entity.getDescription());
        dto.setDetalle(entity.getDetalle());
        dto.setStatus(entity.getStatus());
        dto.setVenue(entity.getVenue());
        dto.setCategory( CategoryMapper.toResponse(entity.getCategory() ) );
        dto.setOrganizer( UserMapper.toDto(entity.getOrganizer() ) );
        dto.setTicketTypes(
                entity.getTicketTypes().stream()
                        .map( TicketTypeMapper::toMinifiedDto )
                        .toList());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }

}
