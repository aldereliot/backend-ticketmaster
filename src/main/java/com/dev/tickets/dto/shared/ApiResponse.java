package com.dev.tickets.dto.shared;

import lombok.Builder;

import java.time.Instant;

public record ApiResponse<T>(
        boolean success,
        T data,
        ApiError error,
        Instant timestamp
){
}
