package com.dev.tickets.exception;

import lombok.Getter;

public class AppException extends RuntimeException{
    private String code;
    public AppException(String code, String message){
        super(message);
        this.code = code;
    }
    public AppException(String message){
        super(message);
        this.code = "APP_EXCEPTION";
    }
    public String getCode() {
        return code;
    }
}
