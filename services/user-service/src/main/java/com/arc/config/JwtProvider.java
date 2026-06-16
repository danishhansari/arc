package com.arc.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class JwtProvider {

    private final SecretKey secretKey;

    public JwtProvider(@Value("${jwt.secret.key}") String secretKey) {
        System.out.println(secretKey);
        System.out.println(secretKey.length());
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }
    public String generateToken(Authentication authentication, UUID userId) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String jwts = Jwts.builder()
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 86400000)) // 24 hrs
                .claim("email", authentication.getName())
                .claim("userId", userId)
                .signWith(secretKey)
                .compact();
        return jwts;
    }
}
