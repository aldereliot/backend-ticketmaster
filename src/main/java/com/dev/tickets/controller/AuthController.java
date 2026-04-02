package com.dev.tickets.controller;

import com.dev.tickets.dto.auth.AuthResponse;
import com.dev.tickets.dto.auth.LoginRequest;
import com.dev.tickets.dto.auth.SignupRequest;
import com.dev.tickets.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request){
        AuthResponse res = authService.login(request);
        return ResponseEntity.ok().body(res);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest request){
        this.authService.signup(request);
        return ResponseEntity.ok().body(Map.of("message", "Cuenta creada correctamente"));
    }

}
