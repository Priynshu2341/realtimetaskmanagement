package com.example.realtimetaskmanagement.controller.normalcontrollers;

import com.example.realtimetaskmanagement.dto.UserDTO;
import com.example.realtimetaskmanagement.entity.Users;
import com.example.realtimetaskmanagement.service.normalservices.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping("/getByUsername/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username){
        Users user = userService.getUserByUsername(username);
        return ResponseEntity.ok(new UserDTO(user.getUsername(),user.getEmail()));
    }

    @GetMapping("/getByEmail/{email}")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email){
        Users user = userService.getUserByEmail(email);
        return ResponseEntity.ok(new UserDTO(user.getUsername(),user.getEmail()));
    }

}
