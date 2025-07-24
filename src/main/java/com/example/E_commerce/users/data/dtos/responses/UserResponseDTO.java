package com.example.E_commerce.users.data.dtos.responses;

import com.example.E_commerce.configs.security.commons.shared.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Builder
@Getter
@Setter
public class UserResponseDTO {
    private UUID id;
    private String name;
    private LocalDate birthday;
    private String profilePictureUrl;
    private Set<Role> roles;
    private String email;
    private String phoneNumber;

}
