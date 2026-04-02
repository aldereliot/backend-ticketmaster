package com.dev.tickets.dto.category;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
public record CategoryResponse(
        Integer id,
        String name,
        String image
) {
}
