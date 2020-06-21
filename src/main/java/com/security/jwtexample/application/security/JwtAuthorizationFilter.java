package com.security.jwtexample.application.security;

import com.security.jwtexample.util.JwtUtil;
import com.security.jwtexample.model.security.UserDetailsModel;
import com.security.jwtexample.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private static final String HEADER_TOKEN_PREFIX = "Bearer";
    private static final String HEADER_AUTHORIZATION = "Authorization";

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        String authorizationHeader = request.getHeader(HEADER_AUTHORIZATION);

        if (authorizationHeader != null && authorizationHeader.startsWith(HEADER_TOKEN_PREFIX)) {

            String token = authorizationHeader.replace(HEADER_TOKEN_PREFIX, "");
            String username = JwtUtil.getUsernameFromToken(token);

            UserDetailsModel userDetails = userService.loadUserByUsername(username);

            if (JwtUtil.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        chain.doFilter(request, response);

    }

}
