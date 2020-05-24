package com.security.jwtexample.domain.model.security;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AuthenticationResponseModel {
    private String jwtToken;
}
