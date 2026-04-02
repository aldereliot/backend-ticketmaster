package com.dev.tickets.controller;

import com.dev.tickets.dto.order.OrderDetailResponse;
import com.dev.tickets.dto.order.OrderMinifiedData;
import com.dev.tickets.dto.order.OrderResumeData;
import com.dev.tickets.dto.ticket.TicketResponse;
import com.dev.tickets.model.OrderDetailEntity;
import com.dev.tickets.model.OrderEntity;
import com.dev.tickets.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{id}/slim")
    public ResponseEntity<?> getOrderMinifiedDataById(@PathVariable String id){
        OrderMinifiedData order = orderService.getOrderMinifiedDataById(id);
        return ResponseEntity.ok().body(order);
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<?> cancelOrder(@PathVariable String id){
        this.orderService.cancelOrder(id);
        return ResponseEntity.ok().body(Map.of("message", "Order cancel successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderDetailById(@PathVariable String id){
        OrderDetailResponse order = orderService.getOrderDetailById(id);
        return ResponseEntity.ok().body(order);
    }

    @GetMapping("/order-details/{id}/tickets")
    public ResponseEntity<?> getTicketsByOrderDetailId(@PathVariable String id){
        List<TicketResponse> tickets = orderService.getTicketsByOrderDetailId(id);
        return ResponseEntity.ok().body(tickets);
    }

    @GetMapping("/my-orders")
    public ResponseEntity<?> getUserOrders(){
        List<OrderEntity> orders = orderService.getUserOrders();
        List<OrderResumeData> data = orders.stream()
                .map( order -> {
                    int ticketsCount = order.getOrderDetails()
                            .stream()
                            .mapToInt( OrderDetailEntity::getQuantity )
                            .sum();

                    return OrderResumeData.builder()
                            .id(order.getId())
                            .total(order.getTotal())
                            .createdAt(order.getCreatedAt())
                            .status(order.getStatus())
                            .itemsCount(ticketsCount)
                            .build();
                })
                .toList();
        return ResponseEntity.ok().body(data);
    }


}
