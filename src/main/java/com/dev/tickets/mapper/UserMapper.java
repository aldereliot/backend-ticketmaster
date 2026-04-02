package com.dev.tickets.mapper;

import com.dev.tickets.dto.auth.SignupRequest;
import com.dev.tickets.dto.user.UserResponse;
import com.dev.tickets.model.RoleEnum;
import com.dev.tickets.model.UserEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public static UserResponse toDto(UserEntity user){
        UserResponse dto = new UserResponse();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setLastname(user.getLastname());
        dto.setEmail(user.getEmail());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setRole(user.getRole());
        return dto;
    }

    public static UserEntity toEntity(  SignupRequest request){
        UserEntity entity = new UserEntity();
        entity.setName(request.name());
        entity.setLastname(request.lastname());
        entity.setEmail(request.email());
        entity.setRole(RoleEnum.ROLE_ATTENDEE);
        return entity;
    }

}
