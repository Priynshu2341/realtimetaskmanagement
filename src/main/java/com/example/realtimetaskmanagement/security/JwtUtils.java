package com.example.realtimetaskmanagement.security;


import com.example.realtimetaskmanagement.entity.Users;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;

@Component
public class JwtUtils {

    private static final String JWT_SECRET = "jwtSecret12312435212312asdasdsaffdgfdger12e12123e123";
    private static final long EXPIRATION_TIME = 1000L * 60 * 60 * 24 * 7;; // 1 minute
    private static final long REFRESH_TOKEN_VALIDITY =  1000L * 60 * 60 * 24 * 30;

    private final Key signingKey = Keys.hmacShaKeyFor(JWT_SECRET.getBytes());

    public String generateToken(Users users) {
        return Jwts.builder()
                .setSubject(users.getUsername())
                .claim("roles", users.getRoleType().toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();

    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String generateRefreshToken(Users users) {
        return Jwts.builder()
                .setSubject(users.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_VALIDITY))
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }


    public Boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(signingKey)
                    .build()
                    .parseClaimsJws(token);
            return true;

        } catch (Exception e) {
            return false;
        }

    }

}
