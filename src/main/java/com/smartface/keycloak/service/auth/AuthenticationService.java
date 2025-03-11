package com.smartface.keycloak.service.auth;

import com.smartface.keycloak.config.keycloak.KeycloakProvider;
import com.smartface.keycloak.dto.auth.AuthenticationRequest;
import com.smartface.keycloak.service.keycloak.KeycloakUserServiceImpl;
import jakarta.ws.rs.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.AccessTokenResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(KeycloakUserServiceImpl.class);

    private final KeycloakProvider kcProvider;

    public AccessTokenResponse authenticate(AuthenticationRequest request) {
        Keycloak keycloak = kcProvider.newKeycloakBuilderWithPasswordCredentials(request.userName(), request.password()).build();
        try {
            return keycloak.tokenManager().getAccessToken();
        } catch (BadRequestException ex) {
            LOGGER.warn("Authentication failed for user: {}. Possible invalid account or unverified email.", request.userName(), ex);
            return null;
        }
    }
}
