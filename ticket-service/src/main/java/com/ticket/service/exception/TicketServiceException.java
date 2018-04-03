package com.ticket.service.exception;

public class TicketServiceException extends Exception {
    public TicketServiceException(String message) {
        super(message);
    }
    TicketServiceException(Throwable throwable) {
        super(throwable);
    }
}