package com.example.realtimetaskmanagement.service.normalservices;


import com.example.realtimetaskmanagement.dto.requestdto.LoginReqDto;
import com.example.realtimetaskmanagement.dto.responsedto.LoginResponseDTO;
import com.example.realtimetaskmanagement.dto.responsedto.UserResponseDTO;
import com.example.realtimetaskmanagement.entity.RoleType;
import com.example.realtimetaskmanagement.entity.Users;
import com.example.realtimetaskmanagement.reps.UserRepository;

import com.example.realtimetaskmanagement.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public void createUser(Users users) {
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        userRepository.save(users);
    }

    public Users getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found With This Name"));
    }

    public Users getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User Not Found With This Email"));
    }

    public void saveUser(Users user) {
        userRepository.save(user);
    }

    public List<Users> findAllUsers() {
        return userRepository.findAll();
    }

}


