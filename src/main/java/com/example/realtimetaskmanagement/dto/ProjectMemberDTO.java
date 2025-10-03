package com.example.realtimetaskmanagement.dto;

import com.example.realtimetaskmanagement.entity.RoleType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectMemberDTO {
    private String username;
    @Enumerated(EnumType.STRING)
    private RoleType roleType;
}
