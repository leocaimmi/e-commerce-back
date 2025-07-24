package com.example.E_commerce.configs.oauth.google;

import com.example.E_commerce.configs.security.commons.data.entities.CredentialEntity;
import com.example.E_commerce.configs.security.commons.data.entities.RoleEntity;
import com.example.E_commerce.configs.security.commons.data.repositories.JpaCredentialRepository;
import com.example.E_commerce.configs.security.commons.data.repositories.JpaRoleRepository;
import com.example.E_commerce.configs.security.commons.shared.Role;
import com.example.E_commerce.configs.security.jwt.services.JwtServiceImpl;
import com.example.E_commerce.users.data.entities.UserEntity;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.util.Optional;
import java.util.Set;

@Configuration
public class OAuth2SuccessHandlerConfig {
    private final JwtServiceImpl jwtService;
    private final JpaCredentialRepository credentialRepository;
    private final JpaRoleRepository roleRepository;

    @Autowired
    public OAuth2SuccessHandlerConfig(JwtServiceImpl service, JpaCredentialRepository credentialRepository, JpaRoleRepository roleRepository) {
        this.jwtService = service;
        this.credentialRepository = credentialRepository;
        this.roleRepository = roleRepository;
    }

    /**
     * Bean for handling OAuth2 authentication success.
     * This method is called when a user successfully authenticates via OAuth2.
     * It checks if the user exists, creates a new user if not, and generates a JWT token.
     */
    @Bean
    @Qualifier("oAuth2Handler")
    public AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler() {
        /*http://localhost:8080/oauth2/authorization/google*/
        return (request, response, authentication) -> {
            OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;

            String email = oauthToken.getPrincipal().getAttribute("email");
            String name = oauthToken.getPrincipal().getAttribute("given_name");
            String picture = oauthToken.getPrincipal().getAttribute("picture");


            // 1. Check if the user already exists
            Optional<CredentialEntity> credentialOpt = credentialRepository.findByUsername(email);
            CredentialEntity credential;

            if (credentialOpt.isEmpty()) {
                // 2. If the user does not exist, create a new user with your credentials
                UserEntity user = createUser(name, picture);

                credential = createCredential(email, user);
                credentialRepository.save(credential);
            } else {
                credential = credentialOpt.get();
            }
            // 3. Generate JWT token
            String jwt = jwtService.generateToken(credential);


            /*response.setContentType("application/json");
            response.getWriter().write("{\"token\": \"" + jwt + "\"}");
            response.getWriter().flush();*/

            //todo when the frontend is ready, redirect to the frontend with the token
            response.setHeader("Authorization", "Bearer "+ jwt);
            response.sendRedirect("http://localhost:4200/login-success?token=" + jwt);
        };
    }

    /**
     * Helper method to create a UserEntity.
     */
    private UserEntity createUser(String name, String picture) {
        return UserEntity.builder()
                .name(name)
                .profilePictureUrl(picture)
                .build();
    }

    /**
     * Helper method to create a CredentialEntity.
     */
    private CredentialEntity createCredential(String email, UserEntity user) {
        RoleEntity role = roleRepository.findByRole(Role.CLIENT)
                .orElseThrow(() -> new EntityNotFoundException("Role CLIENT not found"));

        return CredentialEntity.builder()
                .username(email)
                .password("") // No password for social login
                .user(user)
                .roles(Set.of(role))
                .build();
    }
}
