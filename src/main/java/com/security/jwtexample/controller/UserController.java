package com.security.jwtexample.controller;

import com.security.jwtexample.core.util.JwtUtil;
import com.security.jwtexample.model.security.AuthenticationRequest;
import com.security.jwtexample.model.security.UserDetailsModel;
import com.security.jwtexample.service.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @PostMapping("/login")
    public ResponseEntity doLogin(@RequestBody AuthenticationRequest dto) {
        try {
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword());
            authenticationManager.authenticate(authentication);
        } catch (BadCredentialsException ex) {
            return ResponseEntity.badRequest().body("Usuário ou senha incorretos!");
        }

        UserDetailsModel userDetailsModel = userDetailsService.loadUserByUsername(dto.getUsername());
        String token = JwtUtil.generateToken(userDetailsModel);
        return ResponseEntity.ok(token);
    }

    @GetMapping("/teste")
    public String hello() {
        return "Hello World!";
    }
}
