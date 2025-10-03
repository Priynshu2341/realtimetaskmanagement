package com.example.realtimetaskmanagement.service.normalservices;

import com.example.realtimetaskmanagement.entity.Users;
import com.example.realtimetaskmanagement.reps.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<Users> findAllUsers(){
        return userRepository.findAll();
    }


}
