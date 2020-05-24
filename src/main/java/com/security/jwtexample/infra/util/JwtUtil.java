package com.security.jwtexample.infra.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Base64;
import java.util.Collection;
import java.util.Date;

public class JwtUtil {
    private static final int EXPIRATION_TIME = 1000 * 60 * 60;
    private static final String AUTHORITIES = "authorities";
    private static String SECRET_KEY = "minhasecret";

    public static String generateToken(UserDetails userDetails) {
        String username = userDetails.getUsername();
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

        return Jwts.builder()
                .setSubject(username)
                .claim(AUTHORITIES, authorities)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, encodeBase64(SECRET_KEY))
                .compact();
    }

    public static String getUsernameFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(encodeBase64(SECRET_KEY))
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public static Boolean validateToken(String token, UserDetails userDetails) {
        return getUsernameFromToken(token).equals(userDetails.getUsername()) && !hasTokenExpired(token);
    }

    private static Boolean hasTokenExpired(String token) {
        return Jwts.parser()
                .setSigningKey(encodeBase64(SECRET_KEY))
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .before(new Date());
    }

    public static String encodeBase64(String text) {
        return Base64.getEncoder().encodeToString(text.getBytes());
    }

}
