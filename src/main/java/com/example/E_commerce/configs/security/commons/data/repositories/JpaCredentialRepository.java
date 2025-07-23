package com.example.E_commerce.configs.security.commons.data.repositories;

import com.example.E_commerce.configs.security.commons.data.entities.CredentialEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface JpaCredentialRepository extends JpaRepository<CredentialEntity, UUID> {
    Optional<CredentialEntity> findByUsername(String username);
}
