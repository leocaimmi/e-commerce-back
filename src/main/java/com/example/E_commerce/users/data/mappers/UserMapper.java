package com.example.E_commerce.users.data.mappers;

import com.example.E_commerce.configs.security.commons.data.entities.CredentialEntity;
import com.example.E_commerce.users.data.dtos.responses.UserResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponseDTO toResponse(CredentialEntity credentialEntity) {

        return UserResponseDTO.builder()
                .id(credentialEntity.getId())
                .name(credentialEntity.getUser().getName())
                .profilePictureUrl(credentialEntity.getUser().getProfilePictureUrl())
                .email(credentialEntity.getUsername())
                .phoneNumber(credentialEntity.getPhoneNumber())
                //.roles(credentialEntity.getRoles())
                .build();
    }
}
