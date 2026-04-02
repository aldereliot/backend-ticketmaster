package com.dev.tickets.dto.order;

import com.dev.tickets.model.OrderStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResumeData {
    private String id;
    private BigDecimal total;
    private LocalDateTime createdAt;
    private OrderStatus status;
    private Integer itemsCount;
}
