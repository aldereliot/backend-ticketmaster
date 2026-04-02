package com.dev.tickets.controller;

import com.dev.tickets.dto.ticket.TicketData;
import com.dev.tickets.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @GetMapping("/my-tickets")
    public ResponseEntity<?> getUserTickets(){
        List<TicketData> tickets = ticketService.getUserTickets();
        return ResponseEntity.ok().body(tickets);
    }

}
