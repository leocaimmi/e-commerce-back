package com.example.E_commerce.configs.security.commons.auth.data.dtos.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthLoginRequestDTO {
    @Email //todo add regular expression for email validation
    @Schema(description = "Email", example = "pepito@gmail.com")
    private String username;
    @Schema(description = "password", example = "12345678")
    private String password;
}
