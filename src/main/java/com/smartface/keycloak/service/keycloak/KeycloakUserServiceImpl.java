package com.smartface.keycloak.service.keycloak;

import com.smartface.keycloak.dto.user.CreateUserRequest;
import com.smartface.keycloak.dto.user.ResetPasswordRequest;
import com.smartface.keycloak.exception.*;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class KeycloakUserServiceImpl implements KeycloakUserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(KeycloakUserServiceImpl.class);

    @Value("${keycloak.realm}")
    private String realm;

    private final Keycloak keycloak;

    @Override
    public UserRepresentation createUser(CreateUserRequest request) {
        UserRepresentation user = buildUserRepresentation(request);

        try {
            Response response = keycloak.realm(realm).users().create(user);
            if (response.getStatus() == Response.Status.CREATED.getStatusCode()) {
                return findUserByUsername(request.userName());
            } else {
                String errorMsg = "Failed to create user: " + response.getStatusInfo().getReasonPhrase();
                LOGGER.error(errorMsg);
                throw new UserCreationException(errorMsg);
            }
        } catch (Exception e) {
            LOGGER.error("Error creating user: ", e);
            throw new UserCreationException("Error creating user: " + e.getMessage(), e);
        }
    }

    private UserRepresentation buildUserRepresentation(CreateUserRequest request) {
        UserRepresentation user = new UserRepresentation();
        user.setUsername(request.userName());
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setEmail(request.email());
        user.setEnabled(true);

        CredentialRepresentation credentials = new CredentialRepresentation();
        credentials.setType(CredentialRepresentation.PASSWORD);
        credentials.setValue(request.password());
        credentials.setTemporary(false);

        user.setCredentials(Collections.singletonList(credentials));
        return user;
    }

    private UserRepresentation findUserByUsername(String userName) {
        List<UserRepresentation> users = keycloak.realm(realm).users().search(userName, true);
        return users.stream()
                    .filter(UserRepresentation::isEmailVerified)
                    .findFirst()
                    .orElseThrow(() -> new UserNotFoundException("User not found with username: " + userName));
    }

    @Override
    public UserRepresentation getUserById(String userId) {
        try {
            UserResource userResource = keycloak.realm(realm).users().get(userId);
            return userResource.toRepresentation();
        } catch (Exception e) {
            LOGGER.error("Error fetching user by ID: {}", userId, e);
            throw new UserNotFoundException("Error fetching user by ID: " + userId, e);
        }
    }

    @Override
    public Boolean deleteUserById(String userId) {
        try {
            keycloak.realm(realm).users().delete(userId);
            LOGGER.info("User deleted successfully: {}", userId);
            return true;
        } catch (Exception e) {
            LOGGER.error("Error deleting user by ID: {}", userId, e);
            throw new UserDeletionException("Error deleting user with ID: " + userId, e);
        }
    }

    @Override
    public Boolean emailVerification(String userId) {
        try {
            keycloak.realm(realm).users().get(userId).sendVerifyEmail();
            LOGGER.info("Verification email sent to user: {}", userId);
            return true;
        } catch (Exception e) {
            LOGGER.error("Error sending verification email to user: {}", userId, e);
            throw new EmailVerificationException("Error sending verification email to user: " + userId, e);
        }
    }

    @Override
    public Boolean updatePassword(ResetPasswordRequest resetPasswordRequest, String userId) {
        try {
            UserResource userResource = keycloak.realm(realm).users().get(userId);
            CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
            credentialRepresentation.setValue(resetPasswordRequest.password());
            credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
            credentialRepresentation.setTemporary(false);

            userResource.resetPassword(credentialRepresentation);
            LOGGER.info("Password updated for user: {}", userId);
            return true;
        } catch (Exception e) {
            LOGGER.error("Error updating password for user: {}", userId, e);
            throw new PasswordResetException("Error updating password for user: " + userId, e);
        }
    }
}
