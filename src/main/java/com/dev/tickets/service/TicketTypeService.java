package com.dev.tickets.service;

import com.dev.tickets.exception.AppException;
import com.dev.tickets.model.EventEntity;
import com.dev.tickets.model.TicketEntity;
import com.dev.tickets.model.TicketTypeEntity;
import com.dev.tickets.repository.EventRepository;
import com.dev.tickets.repository.TicketTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketTypeService {

    private final TicketTypeRepository ticketTypeRepository;
    private final EventRepository eventRepository;

    public TicketTypeService(TicketTypeRepository ticketTypeRepository, EventRepository eventRepository) {
        this.ticketTypeRepository = ticketTypeRepository;
        this.eventRepository = eventRepository;
    }

    public void deleteById(Integer id){
        TicketTypeEntity ticketType = ticketTypeRepository.findById(id)
                .orElseThrow( () -> new AppException("Ticket type not found") );
        this.ticketTypeRepository.delete(ticketType);
    }

    public TicketTypeEntity getById(Integer id){
        return ticketTypeRepository.findById(id)
                .orElseThrow( () -> new AppException("Ticket type not found") );
    }

    public List<TicketTypeEntity> getTicketTypesByEvent(Integer id){
        EventEntity event = eventRepository.findByIdWithActiveTickets(id)
                .orElseThrow( () -> new AppException("Ticket not found") );
        return event.getTicketTypes();
    }

}
