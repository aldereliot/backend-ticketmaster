package com.dev.tickets.service;

import com.dev.tickets.config.security.JwtUtils;
import com.dev.tickets.dto.auth.AuthResponse;
import com.dev.tickets.dto.auth.LoginRequest;
import com.dev.tickets.dto.auth.SignupRequest;
import com.dev.tickets.dto.user.UserResponse;
import com.dev.tickets.exception.AppException;
import com.dev.tickets.mapper.UserMapper;
import com.dev.tickets.model.UserEntity;
import com.dev.tickets.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(JwtUtils jwtUtils, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.jwtUtils = jwtUtils;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthResponse login(LoginRequest request) {
        UserEntity user = userRepository.findByEmail(request.email())
                .orElseThrow( () -> new AppException("Credenciales inválidas"));

        if( !passwordEncoder.matches(request.password(), user.getPassword()) ){
            throw new AppException("Credenciales inválidas");
        }

        String token = jwtUtils.generateToken(user.getEmail());
        UserResponse res = UserMapper.toDto(user);
        return new AuthResponse(res, token);
    }

    public void signup(SignupRequest request){
        Optional<UserEntity> existingByEmail = userRepository.findByEmail(request.email());
        if(existingByEmail.isPresent()){
            throw new AppException("El email ya esta en uso");
        }
        UserEntity user = UserMapper.toEntity(request);
        user.setPassword( passwordEncoder.encode(request.password()) );
        this.userRepository.save(user);
    }
}
