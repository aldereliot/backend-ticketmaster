package com.dev.tickets.dto.purchase;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseItemRequest {
    private Integer ticketTypeId;
    private Integer quantity;
}
