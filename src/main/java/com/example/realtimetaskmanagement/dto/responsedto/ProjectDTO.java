package com.example.realtimetaskmanagement.dto.responsedto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private List<String> projectMembersUsername = new ArrayList<>();
}