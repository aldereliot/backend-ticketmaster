package com.dev.tickets.controller;

import com.dev.tickets.dto.checkout.CheckoutRequest;
import com.dev.tickets.service.CheckoutService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {

    private final CheckoutService checkoutService;

    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody CheckoutRequest request){
        String mercadoPagoUrl = checkoutService.createCheckout(request);
        return ResponseEntity.ok().body(mercadoPagoUrl);
    }


}
