package com.dev.tickets.dto.shared;

import lombok.Builder;

@Builder
public record ApiError(
        String code,
        String message
) {}