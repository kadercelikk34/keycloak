package com.smartface.keycloak.service.auth;

import com.smartface.keycloak.config.keycloak.KeycloakProvider;
import com.smartface.keycloak.dto.auth.AuthenticationRequest;
import com.smartface.keycloak.exception.AuthenticationException;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.AccessTokenResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationService.class);

    private final KeycloakProvider kcProvider;

    public AccessTokenResponse authenticate(AuthenticationRequest request) {
        Keycloak keycloak = kcProvider.newKeycloakBuilderWithPasswordCredentials(request.userName(), request.password())
                                      .build();
        try {
            return keycloak.tokenManager().getAccessToken();
        } catch (Exception ex) {
            String errorMessage = "Authentication failed for user: " + request.userName() + ". Possible invalid account or unverified email.";
            LOGGER.warn(errorMessage, ex);
            throw new AuthenticationException(errorMessage, ex);
        }
    }
}

