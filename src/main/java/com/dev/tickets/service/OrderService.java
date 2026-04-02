package com.dev.tickets.service;

import com.dev.tickets.dto.order.*;
import com.dev.tickets.dto.ticket.TicketResponse;
import com.dev.tickets.exception.AppException;
import com.dev.tickets.model.OrderEntity;
import com.dev.tickets.model.OrderDetailEntity;
import com.dev.tickets.model.TicketEntity;
import com.dev.tickets.model.UserEntity;
import com.dev.tickets.repository.OrderRepository;
import com.dev.tickets.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final TicketRepository ticketRepository;
    private final UserService userService;

    public OrderMinifiedData getOrderMinifiedDataById(String orderId){
        return orderRepository.getOrderById(orderId)
                .orElseThrow( () -> new AppException("Order not found "));
    }

    public List<OrderEntity> getUserOrders(){
        UserEntity loggedUser = userService.getUserLogged();
        return orderRepository.getUserOrders(loggedUser.getId());
    }

    public OrderDetailResponse getOrderDetailById(String orderId){
        OrderEntity order = orderRepository.findByIdWithDetails(orderId)
                .orElseThrow( () -> new AppException("Order not found"));

        List<OrderDetailData> orderDetails = order.getOrderDetails().stream()
                .map(this::mapToOrderDetailData)
                .toList();

        return OrderDetailResponse.builder()
                .id(order.getId())
                .total(order.getTotal())
                .status(order.getStatus())
                .createdAt(order.getCreatedAt())
                .orderDetails(orderDetails)
                .build();
    }

    public List<TicketResponse> getTicketsByOrderDetailId(String orderDetailId){
        List<TicketEntity> tickets = ticketRepository.findByOrderDetailId(orderDetailId);
        return tickets.stream()
                .map(ticket -> new TicketResponse(
                        ticket.getId(),
                        ticket.getTicketNumber(),
                        ticket.getStatus(),
                        ticket.getQrImage()
                ))
                .toList();
    }

    private OrderDetailData mapToOrderDetailData(OrderDetailEntity detail){
        var ticketType = detail.getTicketType();
        var event = ticketType.getEvent();

        EventBasicData eventData = EventBasicData.builder()
                .id(event.getId())
                .name(event.getName())
                .date(event.getStartDate())
                .location(event.getLocation())
                .build();

        TicketTypeData ticketTypeData = TicketTypeData.builder()
                .id(ticketType.getId())
                .name(ticketType.getName())
                .price(ticketType.getPrice())
                .event(eventData)
                .build();

        return OrderDetailData.builder()
                .id(detail.getId())
                .quantity(detail.getQuantity())
                .price(detail.getPrice())
                .subtotal(detail.getPrice().multiply(new java.math.BigDecimal(detail.getQuantity())))
                .ticketsCount(detail.getTickets().size())
                .ticketType(ticketTypeData)
                .build();
    }

}
