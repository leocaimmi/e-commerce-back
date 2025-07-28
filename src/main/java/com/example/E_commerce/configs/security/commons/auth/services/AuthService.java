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
import com.example.E_commerce.configs.security.jwt.services.UserDetailsService;
import com.example.E_commerce.users.data.entities.UserEntity;
import com.example.E_commerce.users.data.repositories.JpaUserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class AuthService {

    private final JpaCredentialRepository credentialRepository;
    private final JwtServiceImpl jwtService;
    private final AuthenticationManager authenticationManager;
    private final JpaUserRepository userRepository;
    private final AuthMapper authMapper;
    private final JpaRoleRepository roleRepository;
    private final UserDetailsService userDetailsService;

    @Autowired
    public AuthService(JpaCredentialRepository credentialRepository, JwtServiceImpl jwtService, AuthenticationManager authenticationManager, JpaUserRepository userRepository, AuthMapper authMapper, JpaRoleRepository roleRepository, UserDetailsService userDetailsService) {
        this.credentialRepository = credentialRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.authMapper = authMapper;
        this.roleRepository = roleRepository;
        this.userDetailsService = userDetailsService;
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

        // 1. Check if the email already exists
        if (credentialRepository.findByUsername(request.getEmail()).isPresent()) {
            throw new EntityExistsException("Emails just can be used once");
        }

        // 2. Mapear UserEntity
        UserEntity user = authMapper.toEntity(request);

        // 3. Found a role
        HashSet<RoleEntity> roles = new HashSet<>();
        RoleEntity role = roleRepository.findByRole(request.getRole())
                .orElseThrow(() -> new EntityNotFoundException("Role CLIENT not found"));
        roles.add(role);

        // 4. Map CredentialEntity and set it to UserEntity
        CredentialEntity credential = authMapper.toCredential(request, roles, user);

        // 5. Auth fake user to set the security context for auditing
        UserDetails tempUserDetails = new org.springframework.security.core.userdetails.User(
                request.getEmail(), "", List.of()
        );
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(
                        tempUserDetails, null, tempUserDetails.getAuthorities()
                );
        SecurityContextHolder.getContext().setAuthentication(authToken);

        // 6. Save credential and user (bilateral)
        credentialRepository.save(credential);

        // 7. Generate JWT token
        String jwt = jwtService.generateToken(credential);

        SecurityContextHolder.clearContext();// clear the security context after registration because it was just a fake user for auditing

        return AuthResponseDTO.builder()
                .token(jwt)
                .build();
    }
}
