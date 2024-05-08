package com.spring.clearsolutions.controller;

import com.spring.clearsolutions.exception.DateFromLessThanDateToException;
import com.spring.clearsolutions.exception.UserAgeLessThanMinimalException;
import com.spring.clearsolutions.exception.UserDoesntExistException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.util.List;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
@Log4j2
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error("Handling MethodArgumentNotValidException has thrown");
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("timestamp", new Date());
        responseBody.put("status", status.value());

        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());

        responseBody.put("errors", errors);

        return new ResponseEntity<>(responseBody, headers, status);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    private ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) {
        log.error("Handling ConstraintViolationException has thrown");
        Map<String, Object> responseBody = new LinkedHashMap<>();

        responseBody.put("timestamp", new Date());
        responseBody.put("status", HttpStatus.BAD_REQUEST.value());

        ex.getConstraintViolations().forEach(x -> responseBody.put(x.getPropertyPath().toString(), x.getMessage()));

        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DateFromLessThanDateToException.class)
    private ResponseEntity<Object> handleDateFromLessThanDateToException(DateFromLessThanDateToException ex) {
        log.error("Handling DateFromLessThanDateToException has thrown");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(UserAgeLessThanMinimalException.class)
    private ResponseEntity<Object> handleUserAgeLessThanMinimalException(UserAgeLessThanMinimalException ex) {
        log.error("Handling UserAgeLessThanMinimalException has thrown");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(UserDoesntExistException.class)
    private ResponseEntity<Object> handleUserDoesntExistException(UserDoesntExistException ex) {
        log.error("Handling UserDoesntExistException has thrown");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
