package com.example.E_commerce.configs.security.commons.data.repositories;

import com.example.E_commerce.configs.security.commons.data.entities.PermitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaPermitRepository extends JpaRepository<PermitEntity, UUID> {
}
