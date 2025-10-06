package com.example.realtimetaskmanagement.dto;

import com.example.realtimetaskmanagement.entity.Project;
import com.example.realtimetaskmanagement.entity.Task;
import com.example.realtimetaskmanagement.entity.Users;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {
    private Long id;
    private String title;
    private String description;
    private String priority;
    private String status;
    private LocalDateTime dueDate;
    private String assignee;
    private String projectId;



}