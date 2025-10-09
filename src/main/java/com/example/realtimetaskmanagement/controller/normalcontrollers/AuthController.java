package com.example.realtimetaskmanagement.controller.normalcontrollers;

import com.example.realtimetaskmanagement.dto.requestdto.LoginReqDto;
import com.example.realtimetaskmanagement.dto.responsedto.LoginResponseDTO;
import com.example.realtimetaskmanagement.dto.responsedto.UserResponseDTO;
import com.example.realtimetaskmanagement.entity.Users;
import com.example.realtimetaskmanagement.security.JwtUtils;
import com.example.realtimetaskmanagement.service.normalservices.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody Users users) {
        userService.createUser(users);
        return ResponseEntity.ok("User Creation Success");
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
            return ResponseEntity.ok(new LoginResponseDTO(accessToken, refreshToken, new UserResponseDTO(
                    user.getUsername(), user.getRoleType().toString()
            )));

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


    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody Map<String, String> request) throws Exception {
        try {
            String refreshToken = request.get("refreshToken");
            if (refreshToken == null) throw new Exception("refresh Token Not Found");

            if (!jwtUtils.validateToken(refreshToken)) {
                throw new Exception("Invalid Refresh Token");
            }
            String username = jwtUtils.extractUsername(refreshToken);

            Users user = userService.getUserByUsername(username);
            if (!refreshToken.equals(user.getRefreshToken())) {
                throw new Exception("Invalid refresh token");
            }

            String accessToken = jwtUtils.generateToken(user);
            userService.saveUser(user);
            return ResponseEntity.ok().body(new LoginResponseDTO(accessToken, refreshToken, new UserResponseDTO(
                    username, user.getRoleType().toString()
            )));

        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid Username or Password " + e.getMessage());
        }
    }


}
