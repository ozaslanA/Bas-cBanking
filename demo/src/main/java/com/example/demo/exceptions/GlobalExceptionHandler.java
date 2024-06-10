package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Kendi belirlediğimiz durumlarda fırlattığımız exceptionlar
    @ExceptionHandler
    public ResponseEntity<ErrorDetails> handleException(BankException e, WebRequest request){
        ErrorDetails errorDetails = ErrorDetails.builder()
                .message(e.getMessage())
                .error(request.getDescription(false))
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }


    // kendi istisnalarımız dışındaki exceptionlarda fırlar
    @ExceptionHandler
    public ResponseEntity<ErrorDetails> handleException(Exception e, WebRequest request){
        ErrorDetails errorDetails = ErrorDetails.builder()
                .message(e.getMessage())
                .error(request.getDescription(false))
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }


}
