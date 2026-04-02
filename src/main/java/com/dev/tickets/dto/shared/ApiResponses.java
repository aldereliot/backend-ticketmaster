package com.dev.tickets.dto.shared;

import java.time.Instant;

public class ApiResponses {

    public static <T> ApiResponse<T> success(T data){
        return new ApiResponse<>(true, data, null, Instant.now());
    }

    public static <T> ApiResponse<T> error(String code, String message){
        var error = ApiError.builder()
                .code(code)
                .message(message)
                .build();
        return new ApiResponse<>(false, null, error, Instant.now());
    }

}
