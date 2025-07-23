package com.example.E_commerce.configs.security.jwt.filters;

import com.example.E_commerce.exceptions.dtos.ErrorHandlerResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {


        String errorMessage = switch (authException) {
            case BadCredentialsException e -> "Invalid credentials";
            case DisabledException e -> "Account disabled";
            case LockedException e -> "Account locked";
            case AccountExpiredException e -> "Account expired";
            case CredentialsExpiredException e -> "Credentials expired";
            case InsufficientAuthenticationException e -> "Insufficient authentication";
            case AuthenticationServiceException e -> "Authentication service error";
            default -> "Authentication error: " + authException.getMessage();
        };

        ErrorHandlerResponse errorResponse = ErrorHandlerResponse.builder()
                .error("UNAUTHORIZED")
                .message(errorMessage)
                .details("Access unauthorized")
                .timestamp(LocalDateTime.now())
                .build();

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        objectMapper.findAndRegisterModules();
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));


    }
}
