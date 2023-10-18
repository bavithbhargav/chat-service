package com.bavithbhargav.chatservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        Map<String, String> validationErrors = new HashMap<>();

        for (FieldError fieldError : result.getFieldErrors()) {
            String fieldName = fieldError.getField();
            String errorMessage = fieldError.getDefaultMessage();
            validationErrors.put(fieldName, errorMessage);
        }

        return new ResponseEntity<>(validationErrors, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<String> handleGenericException(Exception ex) {
        return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
