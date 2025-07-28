package com.example.E_commerce.configs;

import com.example.E_commerce.configs.security.commons.data.entities.CredentialEntity;
import com.example.E_commerce.configs.security.commons.data.entities.RoleEntity;
import com.example.E_commerce.configs.security.commons.data.repositories.JpaCredentialRepository;
import com.example.E_commerce.configs.security.commons.data.repositories.JpaRoleRepository;
import com.example.E_commerce.configs.security.commons.shared.Role;
import com.example.E_commerce.products.data.repositories.JpaCategoryRepository;
import com.example.E_commerce.users.data.entities.UserEntity;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Set;

@Component
public class InitializrDataBase {

    private final JpaRoleRepository roleRepository;
    private final JpaCategoryRepository categoryRepository;
    private final JpaCredentialRepository credentialRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public InitializrDataBase(JpaRoleRepository roleRepository, JpaCategoryRepository categoryRepository, JpaCredentialRepository credentialRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.categoryRepository = categoryRepository;
        this.credentialRepository = credentialRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    @Transactional
    public void init() {
        initRoles();
        initAdmin();
        initCategories();
    }

    private void initAdmin() {
        RoleEntity adminRole = roleRepository.findByRole(Role.ADMIN)
                .orElseThrow(() -> new IllegalStateException("Admin role not found"));
        Set<RoleEntity> adminRoles = Set.of(adminRole);
        UserEntity userAdmin = UserEntity.builder()
                .name("admin")
                .birthday(LocalDate.parse("2000-01-01"))
                .profilePictureUrl("https://example.com/admin.jpg")
                .build();

        CredentialEntity credentialAdmin = CredentialEntity.builder()
                .roles(adminRoles)
                .username("admin@gmail.com")
                .password(passwordEncoder.encode("admin"))
                .user(userAdmin)
                .phoneNumber("000000000")
                .build();

        credentialRepository.save(credentialAdmin);


    }

    private void initRoles() {
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

    private void initCategories() {
        if(categoryRepository.findByName("Technology").isEmpty()) {
            categoryRepository.save(
                    com.example.E_commerce.products.data.entities.CategoryEntity.builder()
                            .name("Technology")
                            .build()
            );
        }
        if(categoryRepository.findByName("Clothes").isEmpty()) {
            categoryRepository.save(
                    com.example.E_commerce.products.data.entities.CategoryEntity.builder()
                            .name("Clothes")
                            .build()
            );
        }
        if(categoryRepository.findByName("Food").isEmpty()) {
            categoryRepository.save(
                    com.example.E_commerce.products.data.entities.CategoryEntity.builder()
                            .name("Food")
                            .build()
            );
        }
        if(categoryRepository.findByName("Books").isEmpty()) {
            categoryRepository.save(
                    com.example.E_commerce.products.data.entities.CategoryEntity.builder()
                            .name("Books")
                            .build()
            );
        }
        if(categoryRepository.findByName("Sports").isEmpty()) {
            categoryRepository.save(
                    com.example.E_commerce.products.data.entities.CategoryEntity.builder()
                            .name("Sports")
                            .build()
            );
        }
        if(categoryRepository.findByName("Home & Living").isEmpty()) {
            categoryRepository.save(
                    com.example.E_commerce.products.data.entities.CategoryEntity.builder()
                            .name("Home & Living")
                            .build()
            );
        }
        if(categoryRepository.findByName("Entertainment").isEmpty()) {
            categoryRepository.save(
                    com.example.E_commerce.products.data.entities.CategoryEntity.builder()
                            .name("Entertainment")
                            .build()
            );
        }
        if(categoryRepository.findByName("Gaming").isEmpty()) {
            categoryRepository.save(
                    com.example.E_commerce.products.data.entities.CategoryEntity.builder()
                            .name("Gaming")
                            .build()
            );
        }
        if(categoryRepository.findByName("Wearables").isEmpty()) {
            categoryRepository.save(
                    com.example.E_commerce.products.data.entities.CategoryEntity.builder()
                            .name("Wearables")
                            .build()
            );
        }
        if(categoryRepository.findByName("Personal Care").isEmpty()) {
            categoryRepository.save(
                    com.example.E_commerce.products.data.entities.CategoryEntity.builder()
                            .name("Personal Care")
                            .build()
            );
        }
        if(categoryRepository.findByName("Home Appliances").isEmpty()) {
            categoryRepository.save(
                    com.example.E_commerce.products.data.entities.CategoryEntity.builder()
                            .name("Home Appliances")
                            .build()
            );
        }
    }

}
