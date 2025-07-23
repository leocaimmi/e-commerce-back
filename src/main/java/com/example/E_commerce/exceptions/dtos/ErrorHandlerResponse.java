package com.example.E_commerce.exceptions.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class ErrorHandlerResponse {
    private String error;
    private String message;
    private String details;
    private LocalDateTime timestamp;

    /***
     *
     * @Param status the HTTP status to be set in the response.
     * @returns a ResponseEntity with the specified HTTP status, error, message, and summary.
     * */
    public static ResponseEntity<ErrorHandlerResponse> buildResponse(HttpStatus status, String error, String message, String summary) {
        return ResponseEntity.status(status)
                .body(ErrorHandlerResponse.builder()
                        .error(error)
                        .message(message)
                        .details(summary)
                        .timestamp(LocalDateTime.now())
                        .build());
    }
}
