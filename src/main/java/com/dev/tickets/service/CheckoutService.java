package com.dev.tickets.service;

import com.dev.tickets.dto.checkout.CheckoutRequest;
import com.dev.tickets.exception.AppException;
import com.dev.tickets.model.*;
import com.dev.tickets.repository.OrderRepository;
import com.dev.tickets.repository.ReservationRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CheckoutService {

    private final ReservationRepository reservationRepository;
    private final UserService userService;
    private final OrderRepository orderRepository;
    @Value("${stripe.api.key}")
    private String stripeApiKey;

    @Transactional
    public String createCheckout(CheckoutRequest request) {
        UserEntity loggedUser = userService.getUserLogged();

        ReservationEntity reservation = reservationRepository.findByIdWithLock(request.reservationId())
                .orElseThrow( () -> new AppException("Reservation not found "));

        if( reservation.getStatus() != ReservationStatus.ACTIVE ){
            throw new AppException("Reservation already processed");
        }
        OrderEntity newOrder = getOrderEntity(reservation, loggedUser);

        reservation.getItems().forEach( (item ) -> {
            OrderDetailEntity orderDetail = new OrderDetailEntity();
            orderDetail.setPrice(item.getTicketType().getPrice());
            orderDetail.setQuantity(item.getQuantity());
            orderDetail.setTicketType(item.getTicketType());
            orderDetail.setOrder(newOrder);
            newOrder.getOrderDetails().add(orderDetail);
        });
        // save order
        OrderEntity saved = orderRepository.save(newOrder);
        reservation.setStatus(ReservationStatus.COMPLETED);

        Stripe.apiKey = stripeApiKey;
        List<SessionCreateParams.LineItem> items = new ArrayList<>();
        for( OrderDetailEntity item : saved.getOrderDetails() ){
            items.add(
                    SessionCreateParams.LineItem.builder()
                            .setQuantity(Long.valueOf(item.getQuantity()))
                            .setPriceData(
                                    SessionCreateParams.LineItem.PriceData.builder()
                                            .setCurrency("pen")
                                            .setUnitAmount( item.getPrice().longValue() * 100 )
                                            .setProductData(
                                                    SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                            .setName( item.getTicketType().getName() )
                                                            .build()
                                            )
                                            .build()
                            )
                            .build()
            );
        }
        SessionCreateParams params = SessionCreateParams.builder()
                .addAllLineItem(items)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:5173/success?orderId="+saved.getId())
                .setCancelUrl("http://localhost:5173/cancel?orderId="+saved.getId())
                .putMetadata("orderId", saved.getId())
                .setPaymentIntentData(
                    SessionCreateParams.PaymentIntentData.builder()
                        .putMetadata("orderId", saved.getId()) // para PaymentIntent
                        .build()
                )
                .build();
        try {
            Session session = Session.create(params);
            return session.getUrl();
        } catch (StripeException e) {
            throw new AppException("Error with payment");
        }
    }

    private static OrderEntity getOrderEntity(ReservationEntity reservation, UserEntity loggedUser) {
        if( !reservation.getUser().getId().equals(loggedUser.getId()) ){
            throw new AppException("You are not authorize to this action");
        }
        BigDecimal total = BigDecimal.ZERO;
        for (ReservationItem item : reservation.getItems()) {
            BigDecimal subtotal = item.getTicketType()
                    .getPrice()
                    .multiply(BigDecimal.valueOf(item.getQuantity()));
            total = total.add(subtotal);
        }
        OrderEntity newOrder = new OrderEntity();
        newOrder.setUser(loggedUser);
        newOrder.setTotal(total);
        newOrder.setStatus(OrderStatus.PENDING_PAYMENT);
        return newOrder;
    }

}











