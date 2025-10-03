package com.example.realtimetaskmanagement.dto;

import com.example.realtimetaskmanagement.entity.Users;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.User;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CommentResponseDTO {
    private String content;
    private String username;
}
