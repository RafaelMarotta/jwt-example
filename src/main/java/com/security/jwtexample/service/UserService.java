package com.security.jwtexample.service;

import com.security.jwtexample.domain.model.security.UserDetailsModel;
import com.security.jwtexample.domain.contract.IUserService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    @Override
    public UserDetailsModel loadUserByUsername(String username) throws UsernameNotFoundException {
        return UserDetailsModel.builder().username(username).password("password").build();
    }

}
