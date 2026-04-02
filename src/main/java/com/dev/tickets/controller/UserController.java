package com.dev.tickets.controller;

import com.dev.tickets.dto.user.UserResponse;
import com.dev.tickets.mapper.UserMapper;
import com.dev.tickets.model.UserEntity;
import com.dev.tickets.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/status")
    public ResponseEntity<?> getUserLogged(){
        UserEntity user = userService.getUserLogged();
        UserResponse res = UserMapper.toDto(user);
        return ResponseEntity.ok().body(res);
    }

}
