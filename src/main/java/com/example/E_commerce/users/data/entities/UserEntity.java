package com.example.E_commerce.users.data.entities;

import com.example.E_commerce.configs.security.commons.data.entities.CredentialEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    private LocalDate birthday;
    private String profilePictureUrl;

    @OneToOne(mappedBy = "user",fetch = FetchType.LAZY)
    private CredentialEntity credential;
}
