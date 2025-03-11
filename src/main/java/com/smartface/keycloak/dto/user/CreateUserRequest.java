package com.smartface.keycloak.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateUserRequest(String firstName,
                                String lastName,
                                @NotNull String userName,
                                @NotNull
                                @Pattern(regexp = "\\+\\d+")
                                String phoneNumber,
                                @Pattern(regexp = ".*@.*\\..*")
                                @NotNull()
                                String email,
                                @Size(min = 8)
                                @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z]).+$")
                                @NotBlank
                                String password) {
}
