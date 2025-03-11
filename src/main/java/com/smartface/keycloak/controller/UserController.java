package com.smartface.keycloak.controller;

import com.smartface.keycloak.dto.user.CreateUserRequest;
import com.smartface.keycloak.dto.user.ResetPasswordRequest;
import com.smartface.keycloak.service.keycloak.KeycloakUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final KeycloakUserService keycloakUserService;

    @PostMapping
    public ResponseEntity<UserRepresentation> createUser(@RequestBody @Valid CreateUserRequest request) {
        return ResponseEntity.ok(keycloakUserService.createUser(request));
    }

    @GetMapping
    public ResponseEntity<UserRepresentation> getUser(Principal principal) {
        return ResponseEntity.ok(keycloakUserService.getUserById(principal.getName()));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Boolean> deleteUserById(@PathVariable String userId) {
        keycloakUserService.deleteUserById(userId);
        return ResponseEntity.ok(true);
    }

    @PutMapping("/{userId}/send-verify-email")
    public ResponseEntity<Boolean> sendVerificationEmail(@PathVariable String userId) {
        keycloakUserService.emailVerification(userId);
        return ResponseEntity.ok(true);
    }

    @PutMapping("/change-password")
    public ResponseEntity<Boolean> updatePassword(@RequestBody ResetPasswordRequest request, Principal principal) {
        keycloakUserService.updatePassword(request, principal.getName());
        return ResponseEntity.ok(true);

    }
}
