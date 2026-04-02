package com.dev.tickets.mapper;

import com.dev.tickets.dto.user.UserResponse;
import com.dev.tickets.model.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserStructMapper {
    UserResponse toResponse(UserEntity user);
}
