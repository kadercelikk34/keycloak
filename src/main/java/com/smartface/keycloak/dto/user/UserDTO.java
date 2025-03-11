package com.smartface.keycloak.dto.user;

import lombok.Data;

@Data
public class UserDTO {

    private String userName;

    private String firstName;
    private String lastName;
    private String email;
}
