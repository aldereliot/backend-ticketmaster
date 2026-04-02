package com.dev.tickets.dto;

import com.dev.tickets.dto.category.CategoryResponse;
import com.dev.tickets.dto.event.EventCardResponse;
import lombok.Builder;

import java.util.List;
import java.util.Map;

@Builder
public record HomeResponse(
        List<EventCardResponse> heroEvents,
        List<CategoryResponse> categories,
        Map<String, List<EventCardResponse>> eventsByCategory
) {
}
