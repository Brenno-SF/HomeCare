package com.eng.homecare.controllers;

import com.eng.homecare.exceptions.ForbiddenAccessException;
import com.eng.homecare.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.eng.homecare.response.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ForbiddenAccessException.class)
    public ResponseEntity<ErrorResponse>handleForbidden(ForbiddenAccessException ex, HttpServletRequest request){

        ErrorResponse error = ErrorResponse.of(HttpStatus.FORBIDDEN, ex.getMessage(),request.getRequestURI());

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);

    }
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse>handleNotFound(ResourceNotFoundException ex, HttpServletRequest request){

        ErrorResponse error = ErrorResponse.of(HttpStatus.NOT_FOUND, ex.getMessage(),request.getRequestURI());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);

    }
}
