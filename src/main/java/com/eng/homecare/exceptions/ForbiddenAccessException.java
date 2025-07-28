package com.eng.homecare.exceptions;

public class ForbiddenAccessException extends RuntimeException {
        public ForbiddenAccessException(String message) {
            super(message);
    }
}
