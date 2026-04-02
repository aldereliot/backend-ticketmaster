package com.dev.tickets.controller;

import com.dev.tickets.dto.event.TicketTypeResponse;
import com.dev.tickets.mapper.TicketTypeMapper;
import com.dev.tickets.model.TicketTypeEntity;
import com.dev.tickets.service.TicketTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/ticket-types")
public class TicketTypeController {

    private final TicketTypeService ticketTypeService;

    public TicketTypeController(TicketTypeService ticketTypeService) {
        this.ticketTypeService = ticketTypeService;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Integer id){
        this.ticketTypeService.deleteById(id);
        return ResponseEntity.ok().body(Map.of("message", "Ticket type deleted"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id){
        TicketTypeEntity ticketType = ticketTypeService.getById(id);
        TicketTypeResponse ticketTypeDto = TicketTypeMapper.toDto(ticketType);
        return ResponseEntity.ok().body(ticketTypeDto);
    }


}
