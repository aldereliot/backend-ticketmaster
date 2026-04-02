package com.dev.tickets.controller;

import com.dev.tickets.dto.event.EventRequest;
import com.dev.tickets.dto.event.EventResponse;
import com.dev.tickets.dto.event.TicketTypeResponse;
import com.dev.tickets.mapper.EventMapper;
import com.dev.tickets.mapper.EventStructMapper;
import com.dev.tickets.mapper.TicketTypeMapper;
import com.dev.tickets.model.EventEntity;
import com.dev.tickets.model.TicketTypeEntity;
import com.dev.tickets.service.EventService;
import com.dev.tickets.service.TicketTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;
    private final TicketTypeService ticketTypeService;
    private final EventStructMapper eventStructMapper;

    public EventController(EventService eventService, TicketTypeService ticketTypeService, EventStructMapper eventStructMapper) {
        this.eventService = eventService;
        this.ticketTypeService = ticketTypeService;
        this.eventStructMapper = eventStructMapper;
    }

    @PostMapping
    public ResponseEntity<?> createEvent(@Valid @RequestBody EventRequest request){
        EventEntity event = eventService.createEvent(request);
        EventResponse eventDto = EventMapper.toDto(event);
        return ResponseEntity.ok().body(eventDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEvent(@PathVariable(name = "id") Integer id, @RequestBody EventRequest request){
        EventEntity event = eventService.updateEvent(id, request);
        EventResponse eventDto = EventMapper.toDto(event);
        return ResponseEntity.ok().body(eventDto);
    }

    @GetMapping
    public ResponseEntity<?> getEvents(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
            ){
        List<EventEntity> events = eventService.getEvents(page, size);
        List<EventResponse> eventsDto = EventMapper.listToDto(events);
        return ResponseEntity.ok().body(eventsDto);
    }

    @GetMapping("/slug/{slug}")
    public ResponseEntity<?> getEventBySlug(@PathVariable String slug){
        EventEntity event = eventService.getEventBySlug(slug);
        EventResponse eventDto = EventMapper.toDto(event);
        return ResponseEntity.ok().body(eventDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEventById(@PathVariable Integer id){
        EventEntity event = eventService.getEventById(id);
        EventResponse eventDto = eventStructMapper.toResponse(event);
        return ResponseEntity.ok().body(eventDto);
    }

    @GetMapping("/{id}/ticket-types")
    public ResponseEntity<?> getTicketTypesByEvent(@PathVariable Integer id){
        List<TicketTypeEntity> ticketTypes = ticketTypeService.getTicketTypesByEvent(id);
        List<TicketTypeResponse> list = TicketTypeMapper.listToDto(ticketTypes);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/organizer")
    public ResponseEntity<?> listEventsForOrganizer(){
        List<EventEntity> events = eventService.listEventsForOrganizer();
        List<EventResponse> eventsDto = EventMapper.listToDto(events);
        return ResponseEntity.ok().body(eventsDto);
    }


}
