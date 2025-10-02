package com.example.realtimetaskmanagement.controller;

import com.example.realtimetaskmanagement.dto.LoginReqDto;
import com.example.realtimetaskmanagement.dto.LoginResponseDTO;
import com.example.realtimetaskmanagement.dto.UserDTO;
import com.example.realtimetaskmanagement.entity.Users;
import com.example.realtimetaskmanagement.security.JwtUtils;
import com.example.realtimetaskmanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody Users users) {
        userService.createUser(users);
        return ResponseEntity.ok(new UserDTO(users.getUsername(), users.getEmail()));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginReqDto loginReqDto) throws Exception {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginReqDto.getUsername(), loginReqDto.getPassword()
                    )
            );
            Users user = userService.getUserByUsername(loginReqDto.getUsername());
            String accessToken = jwtUtils.generateToken(user);
            String refreshToken = jwtUtils.generateRefreshToken(user);
            user.setRefreshToken(refreshToken);
            userService.saveUser(user);
            return ResponseEntity.ok(new LoginResponseDTO(accessToken, refreshToken));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("SomeThing Went Wrong");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() throws Exception {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                return ResponseEntity.badRequest().body("Logout Failed");

            }
            String username = authentication.getName();
            Users user = userService.getUserByUsername(username);
            user.setRefreshToken(null);
            userService.saveUser(user);
            return ResponseEntity.ok().body("Logout Success");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Something Went Wrong");
        }
    }


}
