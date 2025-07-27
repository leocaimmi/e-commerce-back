package com.example.E_commerce.users.services;

import com.example.E_commerce.configs.security.commons.data.entities.CredentialEntity;
import com.example.E_commerce.configs.security.commons.data.repositories.JpaCredentialRepository;
import com.example.E_commerce.users.data.dtos.requests.UserPatchRequestDTO;
import com.example.E_commerce.users.data.dtos.responses.UserResponseDTO;
import com.example.E_commerce.users.data.entities.UserEntity;
import com.example.E_commerce.users.data.mappers.UserMapper;
import com.example.E_commerce.users.data.repositories.JpaUserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

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

    /**
     * Updates the user details based on the provided DTO.
     * @param dto The DTO containing the new values for the user.
     * @return The updated UserResponseDTO.
     */

    @Transactional
    public UserResponseDTO updateUserDetails(UserPatchRequestDTO dto) {
        // 1. Get the authenticated user's email
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        // 2. Find the user by email
        CredentialEntity credential = credentialRepository.findByUsername(email)
                .orElseThrow(() -> new EntityNotFoundException("User "+email+" not found"));

        // 3. Control de cambios (evitar escritura si no hay cambios)
        boolean updated = checkAndApplyChanges(credential.getUser(), dto);

        // 4. if there are changes, update the user entity
        if (updated) {
            userRepository.save(credential.getUser());
        }else{
            throw new EntityExistsException("User "+email+" already has the same values");
        }

        // 5. Retornar DTO actualizado
        return userMapper.toResponse(credential);
    }
    /**
     *
     * Check and apply changes to the user entity based on the provided DTO.
     * @param user The user entity to be updated.
     * @param dto The DTO containing the new values for the user.
     * @return true if any changes were applied, false otherwise.
     * */
    private boolean checkAndApplyChanges(UserEntity user, UserPatchRequestDTO dto) {
        boolean updated = false;

        if (!Objects.equals(user.getBirthday(), dto.getBirthday())) {
            user.setBirthday(dto.getBirthday());
            updated = true;
        }

        if (!Objects.equals(user.getProfilePictureUrl(), dto.getProfilePictureUrl())) {
            user.setProfilePictureUrl(dto.getProfilePictureUrl());
            updated = true;
        }

        if (!Objects.equals(user.getName(), capitalize(dto.getName()))) {
            user.setName(capitalize(dto.getName()));
            updated = true;
        }

        return updated;
    }

    /**
     * Capitalizes the first letter of the string and lowercases the rest.
     * @param str The string to be capitalized.
     * @return The capitalized string.
     */
    private String capitalize(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }
}
