package com.dev.tickets.dto.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PublishEventResponse {
    private String name;
    private String slug;
    private String description;
    private String location;
    private String image;
    private LocalDateTime startDate;
    private List<TicketTypeResponse> ticketTypes = new ArrayList<>();
}
