package com.dev.tickets.service;

import com.dev.tickets.dto.ticket.EventBasicData;
import com.dev.tickets.dto.ticket.TicketData;
import com.dev.tickets.dto.ticket.TicketTypeData;
import com.dev.tickets.model.EventEntity;
import com.dev.tickets.model.TicketEntity;
import com.dev.tickets.model.TicketTypeEntity;
import com.dev.tickets.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final UserService userService;

    public List<TicketData> getUserTickets(){
        var user = userService.getUserLogged();
        List<TicketEntity> tickets = new ArrayList<>();

        tickets.addAll(ticketRepository.getUserTickets(user.getId()));

        return tickets.stream()
                .map(this::mapToTicketData)
                .toList();
    }

    private TicketData mapToTicketData(TicketEntity ticket){
        TicketTypeEntity ticketType = ticket.getTicketType();
        EventEntity event = ticketType.getEvent();

        EventBasicData eventData = EventBasicData.builder()
                .id(event.getId())
                .name(event.getName())
                .date(event.getStartDate())
                .location(event.getLocation())
                .build();

        TicketTypeData ticketTypeData = TicketTypeData.builder()
                .id(ticketType.getId())
                .name(ticketType.getName())
                .price(ticketType.getPrice())
                .event(eventData)
                .build();

        return TicketData.builder()
                .id(ticket.getId())
                .ticketNumber(ticket.getTicketNumber())
                .status(ticket.getStatus())
                .qrValue(ticket.getQrValue())
                .qrImage(ticket.getQrImage())
                .ticketType(ticketTypeData)
                .build();
    }

}
