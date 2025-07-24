package com.example.E_commerce.users.services;

import com.example.E_commerce.configs.security.commons.data.repositories.JpaCredentialRepository;
import com.example.E_commerce.users.data.dtos.responses.UserResponseDTO;
import com.example.E_commerce.users.data.mappers.UserMapper;
import com.example.E_commerce.users.data.repositories.JpaUserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final JpaUserRepository userRepository;
    private final JpaCredentialRepository credentialRepository;
    private final UserMapper userMapper;
    @Autowired
    public UserService(JpaUserRepository userRepository, JpaCredentialRepository credentialRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.credentialRepository = credentialRepository;
        this.userMapper = userMapper;
    }

    public UserResponseDTO getUserByEmail(String email) {

        return  userMapper.toResponse(credentialRepository.findByUsername(email)
                .orElseThrow(()-> new EntityNotFoundException("User "+ email+" not found")));

    }
}
