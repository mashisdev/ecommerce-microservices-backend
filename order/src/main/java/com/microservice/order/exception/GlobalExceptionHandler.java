package com.microservice.order.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(WebExchangeBindException.class)
    public Mono<ResponseEntity<ErrorMessage>> handleValidationException(
            WebExchangeBindException ex,
            ServerWebExchange exchange) {

        log.error("Validation error: {}", ex.getMessage());

        Map<String, String> validationErrors = ex.getFieldErrors().stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        DefaultMessageSourceResolvable::getDefaultMessage,
                        (existing, replacement) -> existing));

        ErrorMessage error = new ErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                ex,
                "Request validation failed",
                exchange.getRequest().getPath().toString(),
                validationErrors
        );

        return Mono.just(ResponseEntity.badRequest().body(error));
    }

    @ExceptionHandler(InventoryNotFoundException.class)
    public Mono<ResponseEntity<ErrorMessage>> handleInventoryNotFoundException(
            InventoryNotFoundException ex,
            ServerWebExchange exchange) {

        log.error("Inventory not found error: {}", ex.getMessage());

        ErrorMessage error = new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                ex,
                ex.getMessage(),
                exchange.getRequest().getPath().toString()
        );

        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(error));
    }

    @ExceptionHandler(InsufficientStockException.class)
    public Mono<ResponseEntity<ErrorMessage>> handleInsufficientStockException(
            InsufficientStockException ex,
            ServerWebExchange exchange) {

        log.error("Insufficient stock error: {}", ex.getMessage());

        ErrorMessage error = new ErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                ex,
                ex.getMessage(),
                exchange.getRequest().getPath().toString()
        );

        return Mono.just(ResponseEntity.badRequest().body(error));
    }

    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<ErrorMessage>> handleGenericException(
            Exception ex,
            ServerWebExchange exchange) {

        log.error("An unexpected error occurred: {}", ex.getMessage(), ex);

        ErrorMessage error = new ErrorMessage(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ex,
                "An unexpected error occurred",
                exchange.getRequest().getPath().toString()
        );

        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error));
    }
}
