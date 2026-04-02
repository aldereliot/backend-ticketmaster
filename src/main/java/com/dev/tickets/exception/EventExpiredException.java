package com.dev.tickets.exception;

public class EventExpiredException extends RuntimeException{
    public EventExpiredException(){
        super("El evento ha expirado");
    }
}
