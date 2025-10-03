package com.example.realtimetaskmanagement.dto;

import com.example.realtimetaskmanagement.entity.Users;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskDTO {
    private String title;
    private String description;
    private String username;
}