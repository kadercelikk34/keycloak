package com.smartface.keycloak.service.keycloak;

import com.smartface.keycloak.dto.user.CreateUserRequest;
import com.smartface.keycloak.dto.user.ResetPasswordRequest;
import org.keycloak.representations.idm.UserRepresentation;

public interface KeycloakUserService {

    UserRepresentation createUser(CreateUserRequest request);
    UserRepresentation getUserById(String userId);
    Boolean deleteUserById(String userId);

    Boolean emailVerification(String userId);

    Boolean updatePassword(ResetPasswordRequest resetPasswordRequest, String userId);

}
