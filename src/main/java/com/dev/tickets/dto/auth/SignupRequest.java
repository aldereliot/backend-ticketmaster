package com.dev.tickets.dto.auth;

public record SignupRequest(
        String name,
        String lastname,
        String email,
        String password
) {
}
