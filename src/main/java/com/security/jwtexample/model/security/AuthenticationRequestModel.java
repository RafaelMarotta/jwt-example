package com.security.jwtexample.model.security;

import lombok.Data;

@Data
public class AuthenticationRequestModel {
    private String username;
    private String password;
}
