package com.example.realtimetaskmanagement.controller;

import com.example.realtimetaskmanagement.dto.ProjectDTO;
import com.example.realtimetaskmanagement.entity.Project;
import com.example.realtimetaskmanagement.entity.Users;
import com.example.realtimetaskmanagement.service.ProjectService;
import com.example.realtimetaskmanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/project")
@RequiredArgsConstructor
public class ProjectController {


    private final ProjectService projectService;
   private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<?> createProject(@RequestBody Project project){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Users user = userService.getUserByUsername(auth.getName());
        project.setCreatedBy(user);

        Project saved = projectService.createProject(project);

        ProjectDTO dto = new ProjectDTO();
        dto.setId(saved.getId());
        dto.setTitle(saved.getTitle());
        dto.setDescription(saved.getDescription());
        dto.setCreatedAt(saved.getCreatedAt());
        dto.setEndDate(saved.getEndDate());
        dto.setCreatedByUsername(user.getUsername());

        return ResponseEntity.ok(dto);
    }

}
