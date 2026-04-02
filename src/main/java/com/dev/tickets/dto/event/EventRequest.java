package com.dev.tickets.dto.event;

import com.dev.tickets.model.EventStatusEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventRequest {
    @NotBlank(message = "The ticket name is required")
    private String name;
    private String image;
    @NotBlank(message = "The description is required")
    private String description;
    private String detalle;
    private String location;
    private LocalDateTime startDate;
    private Integer categoryId;
    @NotNull(message = "Event status must be provided")
    private EventStatusEnum status;
    @Valid
    private List<TicketTypeRequest> tickets = new ArrayList<>();
}
