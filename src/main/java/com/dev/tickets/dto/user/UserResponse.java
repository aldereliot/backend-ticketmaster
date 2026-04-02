package com.dev.tickets.dto.user;

import com.dev.tickets.model.RoleEnum;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private Integer id;
    private String name;
    private String lastname;
    private String email;
    private LocalDateTime createdAt;
    private RoleEnum role;
}
