package com.dev.tickets.dto.order;

import com.dev.tickets.model.OrderStatus;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderMinifiedData{
    private String id;
    private BigDecimal total;
    private OrderStatus status;
}
