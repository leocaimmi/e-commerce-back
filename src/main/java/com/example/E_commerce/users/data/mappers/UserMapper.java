package com.example.E_commerce.users.data.mappers;

import com.example.E_commerce.configs.security.commons.data.entities.CredentialEntity;
import com.example.E_commerce.configs.security.commons.data.entities.RoleEntity;
import com.example.E_commerce.configs.security.commons.shared.Role;
import com.example.E_commerce.users.data.dtos.responses.UserResponseDTO;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public UserResponseDTO toResponse(CredentialEntity credentialEntity) {

        return UserResponseDTO.builder()
                .id(credentialEntity.getId())
                .name(credentialEntity.getUser().getName())
                .profilePictureUrl(credentialEntity.getUser().getProfilePictureUrl())
                .email(credentialEntity.getUsername())
                .phoneNumber(credentialEntity.getPhoneNumber())
                .birthday(credentialEntity.getUser().getBirthday())
                .roles(mapRoles(credentialEntity.getRoles()))
                .build();
    }
    private Set<Role> mapRoles(Set<RoleEntity> roleEntities) {
        return roleEntities.stream()
                .map(RoleEntity::getRole)
                .collect(Collectors.toSet());
    }


}
