package com.smartface.keycloak.config.keycloak;

import lombok.Getter;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class KeycloakProvider {

    @Value("${keycloak.auth-server-url}")
    private String keycloakAuthServerUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.resource}")
    private String clientId;

    @Value("${keycloak.credentials.secret}")
    private String clientSecret;

   @Bean
    public Keycloak getInstance() {
            return KeycloakBuilder.builder()
                                  .realm(realm)
                                  .serverUrl(keycloakAuthServerUrl)
                                  .clientId(clientId)
                                  .clientSecret(clientSecret)
                                  .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                                  .build();
        }

    public KeycloakBuilder newKeycloakBuilderWithPasswordCredentials(String username, String password) {
        return KeycloakBuilder.builder() //
                              .realm(realm) //
                              .serverUrl(keycloakAuthServerUrl)//
                              .clientId(clientId) //
                              .clientSecret(clientSecret) //
                              .username(username) //
                              .password(password);
    }
}
