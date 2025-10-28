package com.example.realtimetaskmanagement.security;


import com.example.realtimetaskmanagement.entity.Users;
import com.nimbusds.jose.jca.JCASupport;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;

@Slf4j
@Component
public class JwtUtils {

    private static final String JWT_SECRET = "jwtSecret12312435212312asdasdsaffdgfdger12e12123e123";
    private static final long EXPIRATION_TIME = 1000L * 60;
    ; // 1 minute
    private static final long REFRESH_TOKEN_VALIDITY = 1000L * 60 * 60 * 24 * 30;

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

    public String getProviderType(String registrationId) {
        return switch (registrationId.toLowerCase()) {
            case "google" -> "GOOGLE";
            case "github" -> "GITHUB";
            default -> throw new IllegalArgumentException("UnSupported OAuth2Provider" + registrationId);
        };
    }

    public String getProviderId(OAuth2User user, String registrationID) {
        String providerId = switch (registrationID.toLowerCase()) {
            case "google" -> user.getAttribute("sub");
            case "github" -> user.getAttribute("id").toString();
            default -> {
                log.error("Unsupported Auth2 ProviderId :{}", registrationID);
                throw new IllegalArgumentException("Unsupported Auth2 ProviderId " + registrationID);

            }
        };
        if (providerId == null || providerId.isBlank()) {
            log.error("Unsupported Auth2 ProviderId :{}", registrationID);
            throw new IllegalArgumentException("Unsupported Auth2 ProviderId " + registrationID);
        }
        return providerId;
    }

    public String getUsernameFromOAuth2(OAuth2User user, String registrationId, String providerId) {
        String email = user.getAttribute("email");
        if (email != null && !email.isBlank()) {
            return email;
        }
        return switch (registrationId.toLowerCase()) {
            case "google" -> user.getAttribute("sub");
            case "github" -> user.getAttribute("login");
            default -> providerId;
        };
    }

}
