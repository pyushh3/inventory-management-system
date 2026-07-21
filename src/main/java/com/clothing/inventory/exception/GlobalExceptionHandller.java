package com.clothing.inventory.exception;

import com.clothing.inventory.common.dto.ErrorResponceDto;
import com.clothing.inventory.common.dto.ValidationErrorResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandller {

    @ExceptionHandler(DuplicateResourceException.class)
    ResponseEntity<ErrorResponceDto> DuplicateResourceException(DuplicateResourceException e, HttpServletRequest request) {

        ErrorResponceDto errorResp = new ErrorResponceDto(LocalDateTime.now(),
                HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT.getReasonPhrase(), e.getMessage(), request.getRequestURI());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResp); // response code 409
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    ResponseEntity<ErrorResponceDto> handleResourceNotFoundException(ResourceNotFoundException e, HttpServletRequest request) {

        ErrorResponceDto errorResp = new ErrorResponceDto(LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), e.getMessage(), request.getRequestURI());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResp);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ValidationErrorResponseDto> methodArgumentNotValid(MethodArgumentNotValidException e, HttpServletRequest request) {

        Map<String, String> fieldErrors = new HashMap<>();

        e.getBindingResult().getFieldErrors().forEach(error ->
                fieldErrors.put(error.getField(),error.getDefaultMessage()));

        ValidationErrorResponseDto validationErrorResp = new ValidationErrorResponseDto(LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), "Validation Failed", request.getRequestURI(),
                fieldErrors
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationErrorResp); // response code 400
    }

    @ExceptionHandler(RuntimeException.class)
    ResponseEntity<ErrorResponceDto> handleRuntimeException(RuntimeException e, HttpServletRequest request) {

        ErrorResponceDto errorResp = new ErrorResponceDto(LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e.getMessage(), request.getRequestURI());


        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResp);
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<ErrorResponceDto> handleGenericException(Exception e, HttpServletRequest request) {

        ErrorResponceDto errorResp = new ErrorResponceDto(LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e.getMessage(), request.getRequestURI());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResp);
    }

}
