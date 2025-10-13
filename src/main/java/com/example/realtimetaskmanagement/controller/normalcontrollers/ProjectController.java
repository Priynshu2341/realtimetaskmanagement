package com.example.realtimetaskmanagement.controller.normalcontrollers;

import com.example.realtimetaskmanagement.entity.Project;
import com.example.realtimetaskmanagement.entity.Users;
import com.example.realtimetaskmanagement.service.normalservices.ProjectService;
import com.example.realtimetaskmanagement.service.normalservices.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/project")
@RequiredArgsConstructor
public class ProjectController {


    private final ProjectService projectService;
    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<?> createProject(@RequestBody Project project) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Users user = userService.getUserByUsername(auth.getName());
        project.setCreatedBy(user);

        Project saved = projectService.createProject(project);

        Project dto = new Project();
        dto.setId(saved.getId());
        dto.setTitle(saved.getTitle());
        dto.setDescription(saved.getDescription());
        dto.setCreatedAt(saved.getCreatedAt());
        dto.setEndDate(saved.getEndDate());
        dto.setCreatedBy(user);
        dto.setMembers(saved.getMembers());
        return ResponseEntity.ok(dto);
    }


    @GetMapping("/find/all")
    public ResponseEntity<?> findAllProject() {
        List<Project> projects = projectService.getAllProject();
        return ResponseEntity.ok(projects);


    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProjectById(@PathVariable Long id) {
        projectService.deleteProjectByID(id);
        return ResponseEntity.ok("Project Deleted");
    }

}
