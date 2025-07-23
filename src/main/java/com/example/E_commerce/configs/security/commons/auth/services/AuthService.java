package com.example.E_commerce.configs.security.commons.auth.services;

import com.example.E_commerce.configs.security.commons.auth.data.dtos.requests.AuthLoginRequestDTO;
import com.example.E_commerce.configs.security.commons.auth.data.dtos.requests.AuthRegisterRequestDTO;
import com.example.E_commerce.configs.security.commons.auth.data.dtos.responses.AuthResponseDTO;
import com.example.E_commerce.configs.security.commons.data.entities.CredentialEntity;
import com.example.E_commerce.configs.security.commons.data.entities.RoleEntity;
import com.example.E_commerce.configs.security.commons.data.mappers.AuthMapper;
import com.example.E_commerce.configs.security.commons.data.repositories.JpaCredentialRepository;
import com.example.E_commerce.configs.security.commons.data.repositories.JpaRoleRepository;
import com.example.E_commerce.configs.security.jwt.services.JwtServiceImpl;
import com.example.E_commerce.users.data.entities.UserEntity;
import com.example.E_commerce.users.data.repositories.JpaUserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class AuthService {

    private final JpaCredentialRepository credentialRepository;
    private final JwtServiceImpl jwtService;
    private final AuthenticationManager authenticationManager;
    private final JpaUserRepository userRepository;
    private final AuthMapper authMapper;
    private final JpaRoleRepository roleRepository;

    @Autowired
    public AuthService(JpaCredentialRepository credentialRepository, JwtServiceImpl jwtService, AuthenticationManager authenticationManager, JpaUserRepository userRepository, AuthMapper authMapper, JpaRoleRepository roleRepository) {
        this.credentialRepository = credentialRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.authMapper = authMapper;
        this.roleRepository = roleRepository;
    }

    public AuthResponseDTO authenticate(AuthLoginRequestDTO request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword())
        );

        CredentialEntity credential = credentialRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("User "+request.getUsername()+" not found"));

        return AuthResponseDTO.builder()
                .token(jwtService.generateToken(credential))
                .build();
    }

    /**
     * Register a new user.
     * */
    @Transactional
    public AuthResponseDTO register(AuthRegisterRequestDTO request) {
        // Verify if the email is already used
        if (credentialRepository.findByUsername(request.getEmail()).isPresent()) {
            throw new EntityExistsException("Emails just can be used once");
        }

        //first create the user
        UserEntity user = authMapper.toEntity(request);
        userRepository.save(user);

        //Second, find the roles for the credential
        HashSet<RoleEntity> roles = new HashSet<>();

        RoleEntity role = roleRepository.findByRole(request.getRole())
                .orElseThrow(() -> new EntityNotFoundException("Role CLIENT not found"));
        roles.add(role);

        //third, create the credential with the user and roles
        CredentialEntity credential = authMapper.toCredential(request,roles,user);
        credentialRepository.save(credential);

        String jwt = jwtService.generateToken(credential);

        return AuthResponseDTO.builder()
                .token(jwt)
                .build();
    }
}
