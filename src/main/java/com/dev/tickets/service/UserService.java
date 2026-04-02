package com.dev.tickets.service;

import com.dev.tickets.config.security.UserDetailsImpl;
import com.dev.tickets.dto.user.UserResponse;
import com.dev.tickets.exception.AppException;
import com.dev.tickets.model.UserEntity;
import com.dev.tickets.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity getUserLogged(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String email = userDetails.getUser().getEmail();
        System.out.println( "Email que tiene: " + email );
        return userRepository.findByEmail(email)
                .orElseThrow( () -> new AppException("User not found"));
    }

}
