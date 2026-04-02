package com.dev.tickets.service;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.cloudinary.Cloudinary;
import com.dev.tickets.exception.AppException;
import com.dev.tickets.libs.Utils;
import com.dev.tickets.model.*;
import com.dev.tickets.repository.OrderRepository;
import com.dev.tickets.repository.TicketRepository;
import com.dev.tickets.repository.TicketTypeRepository;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WebhookService {

    private final Cloudinary cloudinary;
    private final OrderRepository orderRepository;
    private final TicketRepository ticketRepository;
    private final TicketTypeRepository ticketTypeRepository;
    @Value("${STRIPE_WEBHOOK_SECRET}")
    private String webhookSecret;

    @Transactional
    public void processWebhook(String payload, String header, HttpServletRequest request) throws Exception {

        Event event;
        try{
            event = Webhook.constructEvent(payload, header, webhookSecret);
        }catch( SignatureVerificationException e){
            throw new AppException("Signature verification failed");
        }

        switch (event.getType()){
            case "checkout.session.completed":
                System.out.println("✅ checkout.session.completed ");
                var session = (Session) event.getDataObjectDeserializer()
                        .getObject()
                        .orElse(null);
                if (session != null) {
                    String orderId = session.getMetadata().get("orderId");
                    OrderEntity order = orderRepository.findByIdWithLock(orderId)
                                    .orElseThrow( () -> new AppException("Order not found") );
                    if( order.getStatus().equals(OrderStatus.PAID) ) return;
                    order.setStatus(OrderStatus.PAID);
                    for(OrderDetailEntity item : order.getOrderDetails() ){
                        TicketTypeEntity ticketType = item.getTicketType();
                        ticketType.setReserved(ticketType.getReserved() - item.getQuantity());
                        ticketType.setSold(ticketType.getSold() + item.getQuantity());
                        ticketTypeRepository.save(ticketType);
                        for(int i = 0; i < item.getQuantity(); i++ ){
                            String ticketCode = Utils.generateCode();
                            TicketEntity ticket = new TicketEntity();
                            ticket.setTicketNumber(ticketCode);
                            ticket.setStatus(TicketStatusEnum.VALID);
                            ticket.setQrValue(ticketCode);
                            ticket.setTicketType(item.getTicketType());
                            ticket.setOrderDetail(item);
                            var saved = ticketRepository.save(ticket);
                            this.generateQrForTicket(saved.getId());
                        }
                    }
                    orderRepository.save(order);
                }
                break;
            case "payment_intent.payment_failed":
                System.out.println(" ❌ payment intent failed: intent id: " );
                var intent = (PaymentIntent) event.getDataObjectDeserializer()
                        .getObject()
                        .orElse(null);
                if (intent != null) {
                    String orderId = intent.getMetadata().get("orderId");
                    OrderEntity order = orderRepository.findById(orderId)
                            .orElseThrow( () -> new AppException("Order not found") );
                    if( order.getStatus().equals(OrderStatus.CANCELLED) ) return;
                    order.setStatus(OrderStatus.CANCELLED);
                    orderRepository.save(order);
                }
                break;
            default:
                System.out.println("unhandled event type: " + event.getType() );
        }
    }

    @Async
    public void generateQrForTicket(Integer ticketId){
        TicketEntity ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
        byte[] qr = Utils.generateQrImage(ticket.getQrValue());
        String url = uploadQrToCloudinary(qr);

        ticket.setQrImage(url);
        ticketRepository.save(ticket);
    }

    public String uploadQrToCloudinary(byte[] imageBytes ){
        Map uploadResult = null;
        try {
            uploadResult = cloudinary.uploader().upload(imageBytes, Map.of());
        } catch (IOException e) {
            throw new AppException("No se pudo subir el qr");
        }
        return (String) uploadResult.get("secure_url");
    }

}















