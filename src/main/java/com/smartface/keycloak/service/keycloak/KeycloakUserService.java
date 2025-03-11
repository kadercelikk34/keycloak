package com.smartface.keycloak.service.keycloak;

import com.smartface.keycloak.dto.user.CreateUserRequest;
import com.smartface.keycloak.dto.user.ResetPassword;
import org.keycloak.representations.idm.UserRepresentation;

public interface KeycloakUserService {

    UserRepresentation createUser(CreateUserRequest request);
    UserRepresentation getUserById(String userId);
    void deleteUserById(String userId);

    void emailVerification(String userId);

    void updatePassword(ResetPassword resetPassword, String userId);

}
