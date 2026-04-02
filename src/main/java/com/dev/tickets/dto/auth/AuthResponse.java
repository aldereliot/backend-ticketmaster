package com.dev.tickets.dto.auth;

import com.dev.tickets.dto.user.UserResponse;

public record AuthResponse(UserResponse user, String token) {
}
