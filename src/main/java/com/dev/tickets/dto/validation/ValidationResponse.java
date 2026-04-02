package com.dev.tickets.dto.validation;

import com.dev.tickets.dto.ticket.TicketResponse;
import com.dev.tickets.model.TicketValidationMethodEnum;
import com.dev.tickets.model.TicketValidationStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ValidationResponse {
    private Integer id;
    private TicketValidationStatusEnum status;
    private TicketValidationMethodEnum validationMethod;
    private TicketResponse ticket;
    private LocalDateTime createdAt;
}
