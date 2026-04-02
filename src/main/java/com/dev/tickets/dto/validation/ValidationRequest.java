package com.dev.tickets.dto.validation;

import com.dev.tickets.model.TicketValidationMethodEnum;
import com.dev.tickets.model.TicketValidationStatusEnum;

public record ValidationRequest(
        String code,
        TicketValidationMethodEnum method
) {
}
