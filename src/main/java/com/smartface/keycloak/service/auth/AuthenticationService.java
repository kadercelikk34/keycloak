package com.smartface.keycloak.service.auth;

import com.smartface.keycloak.config.keycloak.KeycloakProvider;
import com.smartface.keycloak.dto.auth.AuthenticationRequest;
import jakarta.ws.rs.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final KeycloakProvider kcProvider;

    public AccessTokenResponse authenticate(AuthenticationRequest request) {
        Keycloak keycloak = kcProvider.newKeycloakBuilderWithPasswordCredentials(request.userName(), request.password()).build();
        try {
            return keycloak.tokenManager().getAccessToken();
        } catch (BadRequestException ex) {
            //LOG.warn("invalid account. User probably hasn't verified email.", ex);
            return null;
        }
    }
}
