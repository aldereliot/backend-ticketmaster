package com.dev.tickets.dto.category;

import lombok.Builder;

@Builder
public record CategoryRequest(
        String name,
        String image
) {
}
