package com.dev.tickets.dto.category;

import com.dev.tickets.dto.event.EventCardResponse;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryWithEventsResponse {
    private Integer id;
    private String name;
    private String image;
    private List<EventCardResponse> events;
}
