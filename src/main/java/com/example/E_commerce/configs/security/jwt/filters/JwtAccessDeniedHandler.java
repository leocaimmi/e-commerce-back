package com.example.E_commerce.configs.security.jwt.filters;

import com.example.E_commerce.exceptions.dtos.ErrorHandlerResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {

        ErrorHandlerResponse errorResponse = ErrorHandlerResponse.builder()
                .error("Access denied")
                .message("You do not have sufficient permissions to access this resource")
                .details("The server understood the request but refuses to authorize it due to insufficient permissions")
                .timestamp(LocalDateTime.now())
                .build();

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        objectMapper.findAndRegisterModules();
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));

    }
}
