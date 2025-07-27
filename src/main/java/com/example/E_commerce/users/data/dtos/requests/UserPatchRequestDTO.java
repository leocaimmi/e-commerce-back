package com.example.E_commerce.users.data.dtos.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Past;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
@Schema(description = "User patch request")
public class UserPatchRequestDTO {

    @Schema(description = "User name", example = "john_doe")
    private String name;

    @Past(message = "Birthday date must be in the past")
    @Schema(description = "User birthday date", example = "1990-01-01")
    private LocalDate birthday;

    @Schema(description = "User profile picture URL", example = "https://example.com/profile.jpg")
    private String profilePictureUrl;
}
