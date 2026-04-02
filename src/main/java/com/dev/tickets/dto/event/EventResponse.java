package com.dev.tickets.dto.event;

import com.dev.tickets.dto.category.CategoryResponse;
import com.dev.tickets.dto.user.UserResponse;
import com.dev.tickets.model.EventStatusEnum;
import com.dev.tickets.model.VenueEntity;
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
public class EventResponse {
    private Integer id;
    private String name;
    private String slug;
    private String image;
    private String location;
    private LocalDateTime startDate;
    private String description;
    private String detalle;
    private EventStatusEnum status;
    private VenueEntity venue;
    private CategoryResponse category;
    private UserResponse organizer;
    private List<TicketTypeResponse> ticketTypes = new ArrayList<>();
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
