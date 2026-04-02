package com.dev.tickets.dto.auth;

public record LoginRequest(
        String email,
        String password
) {
}
