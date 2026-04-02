package com.dev.tickets.controller;

import com.dev.tickets.dto.category.CategoryWithEventsResponse;
import com.dev.tickets.dto.event.EventCardResponse;
import com.dev.tickets.dto.event.EventMinifiedResponse;
import com.dev.tickets.dto.event.EventResponse;
import com.dev.tickets.mapper.EventMapper;
import com.dev.tickets.model.EventEntity;
import com.dev.tickets.service.EventService;
import org.apache.coyote.Response;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("/api/published-events")
public class PublishedEventController {

    private final EventService eventService;

    public PublishedEventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public ResponseEntity<?> listPublishedEvents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String category
    ){
        List<EventEntity> events = eventService.listPublishedEvents(page, size, category);
        List<EventResponse> list = EventMapper.listToDto(events);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/category/{name}")
    public ResponseEntity<?> listEventsByCategory(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @PathVariable(name = "name") String category
    ){
        CategoryWithEventsResponse data = eventService.eventsByCategory(page, size, category);
        return ResponseEntity.ok().body(data);
    }

    @GetMapping("/search/{query}")
    public ResponseEntity<?> searchPublishedEvents(@PathVariable String query){
        List<EventCardResponse> events = eventService.searchPublishedEvents(query);
        return ResponseEntity.ok().body(events);
    }

    @GetMapping("/{slug}")
    public ResponseEntity<?> getPublishedEvent(@PathVariable String slug){
        EventEntity event = eventService.getPublishedEvent(slug);
        EventMinifiedResponse dto = EventMapper.toMinifiedDto(event);
        return ResponseEntity.ok().body(dto);
     }

}
