package com.example.realtimetaskmanagement.service.normalservices;

import com.example.realtimetaskmanagement.dto.responsedto.LoginResponseDTO;
import com.example.realtimetaskmanagement.dto.responsedto.UserResponseDTO;
import com.example.realtimetaskmanagement.entity.RoleType;
import com.example.realtimetaskmanagement.entity.Users;
import com.example.realtimetaskmanagement.reps.UserRepository;
import com.example.realtimetaskmanagement.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
@RequiredArgsConstructor
@Service
public class AuthService {

    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public ResponseEntity<LoginResponseDTO> handleOAuth2LoginRequest(OAuth2User oAuth2User, String registrationId) {
        String providerType = jwtUtils.getProviderType(registrationId);
        String providerId = jwtUtils.getProviderId(oAuth2User, registrationId);
        String email = oAuth2User.getAttribute("email");

        Users existingUser = userRepository.findByProviderIdAndProviderType(providerId, providerType).orElse(null);
        Users emailUser = userRepository.findByEmail(email).orElse(null);
        Users finalUser;

        if (existingUser == null && emailUser == null) {
            Users newUser = new Users();
            newUser.setEmail(email);
            newUser.setUsername(jwtUtils.getUsernameFromOAuth2(oAuth2User, registrationId, providerId));
            newUser.setProviderId(providerId);
            newUser.setProviderType(providerType);
            newUser.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
            newUser.setRoleType(RoleType.USER);
            finalUser = userRepository.save(newUser);
        } else if (existingUser != null) {
            if (email != null && !email.isBlank() && !email.equals(existingUser.getEmail())) {
                existingUser.setEmail(email);
            }
            finalUser = existingUser;
        } else {
            throw new BadCredentialsException(
                    "Email already registered using another provider: " + emailUser.getProviderType()
            );
        }

        String accessToken = jwtUtils.generateToken(finalUser);
        String refreshToken = jwtUtils.generateRefreshToken(finalUser);

        String role = finalUser.getRoleType() != null ? finalUser.getRoleType().toString() : "USER";
        LoginResponseDTO response = new LoginResponseDTO();
        response.setAccessToken(accessToken);
        response.setRefreshToken(refreshToken);
        response.setUser(new UserResponseDTO(finalUser.getUsername(), role));

        return ResponseEntity.ok(response);
    }

}
