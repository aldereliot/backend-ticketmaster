package com.dev.tickets.dto.event;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventCardResponse {
    private Integer id;
    private String name;
    private String slug;
    private String image;
    private LocalDateTime startDate;
    private String location;
}
