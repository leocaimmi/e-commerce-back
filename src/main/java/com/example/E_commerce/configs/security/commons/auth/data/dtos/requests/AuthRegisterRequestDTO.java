package com.example.E_commerce.configs.security.commons.auth.data.dtos.requests;

import com.example.E_commerce.configs.security.commons.shared.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthRegisterRequestDTO {

    @NotBlank(message = "Name does not be empty")
    @Schema(description = "Users name", example = "john_doe")
    private String name;
    @Past(message = "Birthday date must be in the past")
    @Schema(description = "Users birthday date", example = "1990-01-01")
    private LocalDate birthday;

    @Schema(description = "Users profile picture URL", example = "https://example.com/profile.jpg")
    private String profilePictureUrl;

    @NotBlank(message = "Email does not be empty")
    @Email //todo use regular expression for email validation
    @Schema(description = "Users email", example = "john_doe@example.com")
    private String email;

    @NotBlank(message = "password does not be empty")
    @Schema(description = "Users password", example = "password123")
    private String password;

    @Schema(description = "Users phone number", example = "1234567890")
    private String phoneNumber;

    @Schema(description = "Users role", example = "CLIENT")
    private Role role;
}
