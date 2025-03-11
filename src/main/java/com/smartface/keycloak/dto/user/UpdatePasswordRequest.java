package com.smartface.keycloak.dto.user;

import jakarta.validation.constraints.NotEmpty;

public record UpdatePasswordRequest(@NotEmpty String currentPassword, @NotEmpty String newPassword){
}
