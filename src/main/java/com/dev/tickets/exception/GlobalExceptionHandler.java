package com.dev.tickets.exception;

import com.dev.tickets.dto.shared.ApiResponse;
import com.dev.tickets.dto.shared.ApiResponses;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentialsException(BadCredentialsException e){
        return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
    }

    @ExceptionHandler(AppException.class)
    public ResponseEntity<?> handleAppException(AppException e){
        var apiError = ApiResponses.error(e.getCode(), e.getMessage());
        return ResponseEntity.badRequest().body(apiError);
    }

    @ExceptionHandler(EventExpiredException.class)
    public ResponseEntity<?> handleEventExpiredException(EventExpiredException ex){
        var apiError = ApiResponses.error("EVENT_EXPIRED", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiError);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> handleUserNotFound(UsernameNotFoundException e){
        return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolation(ConstraintViolationException e){
        log.error("Caught ConstraintViolationException", e);
        String errMessage = e.getConstraintViolations()
                .stream()
                .findFirst()
                .map( violation ->
                    violation.getPropertyPath() + ": " + violation.getMessage()
                ).orElse("Constraint violation ocurred");
        return ResponseEntity.badRequest().body(Map.of("message", errMessage));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleInvalidEnum(HttpMessageNotReadableException ex) {
        Throwable cause = ex.getCause();
        if (cause instanceof InvalidFormatException ife &&
                ife.getTargetType().isEnum()) {

            return ResponseEntity.badRequest().body(
                    Map.of(
                            "message", "Invalid enum value",
                            "allowedValues", ife.getTargetType().getEnumConstants()
                    )
            );
        }
        return ResponseEntity.badRequest().body(
                Map.of("message", "Malformed JSON request")
        );
    }

}
