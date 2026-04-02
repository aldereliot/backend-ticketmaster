package com.dev.tickets.dto.checkout;

import lombok.Builder;

@Builder
public record CheckoutRequest(
        String reservationId
) {
}
