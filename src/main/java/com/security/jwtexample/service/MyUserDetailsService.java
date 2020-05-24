package com.security.jwtexample.service;

import com.security.jwtexample.model.security.UserDetailsModel;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Override
    public UserDetailsModel loadUserByUsername(String username) throws UsernameNotFoundException {
        return UserDetailsModel.builder().username(username).password("password").build();
    }

}
