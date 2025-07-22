package com.eng.homecare.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbiddenAccessException extends RuntimeException {
    public ForbiddenAccessException() {
        super("Você não tem permissão para acessar este recurso.");
    }

    public ForbiddenAccessException(String message) {
        super(message);
    }
}
