package com.example.E_commerce.configs;

import com.example.E_commerce.configs.security.commons.data.entities.RoleEntity;
import com.example.E_commerce.configs.security.commons.data.repositories.JpaRoleRepository;
import com.example.E_commerce.configs.security.commons.shared.Role;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InitializrDataBase {

    private final JpaRoleRepository roleRepository;

    @Autowired
    public InitializrDataBase(JpaRoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    public void init() {
        initRoles();
    }

    public void initRoles() {
        if (roleRepository.findByRole(Role.CLIENT).isEmpty()) {
            roleRepository.save(RoleEntity.builder()
                    .role(Role.CLIENT)
                    .build());
        }
        if (roleRepository.findByRole(Role.ADMIN).isEmpty()) {
            roleRepository.save(RoleEntity.builder()
                    .role(Role.ADMIN)
                    .build());
        }
    }

}
