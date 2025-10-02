package com.example.realtimetaskmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProjectDTO {
    private Long id;
    private String title;
    private LocalDateTime createdAt;
    private LocalDateTime endDate;
    private String description;
    private String createdByUsername;
}