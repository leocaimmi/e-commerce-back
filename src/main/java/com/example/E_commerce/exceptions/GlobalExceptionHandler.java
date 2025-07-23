package com.example.E_commerce.exceptions;

import com.example.E_commerce.exceptions.dtos.ErrorHandlerResponse;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.example.E_commerce.exceptions.dtos.ErrorHandlerResponse.buildResponse;

@Hidden // This annotation hides the class from Swagger documentation
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorHandlerResponse> handleEntityNotFoundException(EntityNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, "Resource not found", "The resource could not be found in the system.", ex.getMessage());
    }
    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<ErrorHandlerResponse> handleEntityExistsException(EntityExistsException ex) {
        return buildResponse(HttpStatus.CONFLICT, "Resource already exists", "The resource already exists in the system.", ex.getMessage());
    }
}
