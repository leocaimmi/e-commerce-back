package com.example.E_commerce.users.controllers;

import com.example.E_commerce.users.data.dtos.requests.UserPatchRequestDTO;
import com.example.E_commerce.users.data.dtos.responses.UserResponseDTO;
import com.example.E_commerce.users.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/v1/users")
@RestController
@Tag(name = "Users", description = "Controller for user management")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Get user by email", description = "Fetches user details based on the provided email.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "User found and returned successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "User not found with the provided email")
    })
    @GetMapping
    public ResponseEntity<UserResponseDTO> getUserByEmail(@RequestParam(name = "email") String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @PatchMapping("/me")
    @Operation(summary = "Update user details", description = "Updates the user details based on the provided information.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "User details updated successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "User not found with the provided email")
    })
    public ResponseEntity<UserResponseDTO> updateUserDetails(@Valid @RequestBody UserPatchRequestDTO userPatchRequestDTO) {
        UserResponseDTO updatedUser = userService.updateUserDetails(userPatchRequestDTO);
        return ResponseEntity.ok(updatedUser);
    }

}
