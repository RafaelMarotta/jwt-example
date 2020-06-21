package com.security.jwtexample.service;

import com.security.jwtexample.model.security.AuthenticationRequestModel;
import com.security.jwtexample.model.security.AuthenticationResponseModel;
import com.security.jwtexample.model.security.UserDetailsModel;
import com.security.jwtexample.util.JwtUtil;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Override
    public UserDetailsModel loadUserByUsername(String username) throws UsernameNotFoundException {

        return UserDetailsModel
                .builder()
                .username(username)
                .password(new BCryptPasswordEncoder().encode("password"))
                .build();

    }

    public AuthenticationResponseModel generateTokenByUsername(AuthenticationRequestModel dto) {

        UserDetailsModel userDetailsModel = loadUserByUsername(dto.getUsername());
        String token = JwtUtil.generateToken(userDetailsModel);

        return AuthenticationResponseModel.builder().jwtToken(token).build();

    }


}
