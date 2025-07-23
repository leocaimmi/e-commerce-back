package com.example.E_commerce.configs.security.commons.data.mappers;

import com.example.E_commerce.configs.security.commons.auth.data.dtos.requests.AuthRegisterRequestDTO;
import com.example.E_commerce.configs.security.commons.data.entities.CredentialEntity;
import com.example.E_commerce.configs.security.commons.data.entities.RoleEntity;
import com.example.E_commerce.users.data.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class AuthMapper {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    public UserEntity toEntity(AuthRegisterRequestDTO userRequestDTO){

        return UserEntity.builder()
                .name(capitalizeString(userRequestDTO.getName()))
                .birthday(userRequestDTO.getBirthday())
                .profilePictureUrl(userRequestDTO.getProfilePictureUrl())
                .build();
    }

    public CredentialEntity toCredential(AuthRegisterRequestDTO userRequestDTO, HashSet<RoleEntity> roles, UserEntity userEntity){
        return CredentialEntity.builder()
                .username(userRequestDTO.getEmail())
                .password(passwordEncoder.encode(userRequestDTO.getPassword()))
                .roles(roles)
                .phoneNumber(userRequestDTO.getPhoneNumber())
                .user(userEntity)
                .build();
    }
    /**
     * Capital the first letter of a string and lowercases the rest.
     * @param str the string to capitalize
     * @return the capitalized string, or the original string if it is null or empty
     */
    private String capitalizeString(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

}