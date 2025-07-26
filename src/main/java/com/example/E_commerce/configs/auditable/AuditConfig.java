package com.example.E_commerce.configs.auditable;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

import java.util.Map;
import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class AuditConfig {
    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.isAuthenticated() && !auth.getPrincipal().equals("anonymousUser")) {
                if (auth instanceof OAuth2AuthenticationToken oAuth2Token) {
                    Map<String, Object> attributes = oAuth2Token.getPrincipal().getAttributes();
                    return Optional.ofNullable((String) attributes.get("email"));
                } else {
                    return Optional.ofNullable(auth.getName()); // JWT propio o login con username
                }
            }
            return Optional.empty();
        };
    }
}
