package com.example.realtimetaskmanagement.controller.pagingcontrollers;

import com.example.realtimetaskmanagement.dto.responsedto.ProjectDTO;
import com.example.realtimetaskmanagement.entity.Project;
import com.example.realtimetaskmanagement.entity.Users;
import com.example.realtimetaskmanagement.reps.ProjectRepository;
import com.example.realtimetaskmanagement.reps.UserRepository;
import com.example.realtimetaskmanagement.service.pagingservice.PagingProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/paging/project")
@RequiredArgsConstructor
public class PagingProjectController {

    private final PagingProjectService pagingProjectService;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;


    @GetMapping("/all")
    public ResponseEntity<?> getAllProjectsPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        List<Project> projects = pagingProjectService.getAllProjectPaged(page, size);
        long totalElements = projectRepository.count();
        List<ProjectDTO> projectDTOS = projects.stream().map(project -> {
            List<String> memberUsernames = project.getMembers().stream()
                    .map(member -> member.getUsers().getUsername())
                    .toList();

            return new ProjectDTO(
                    project.getId(),
                    project.getTitle(),
                    project.getCreatedAt(),
                    project.getEndDate(),
                    project.getDescription(),
                    project.getCreatedBy().getUsername(),
                    memberUsernames
            );
        }).toList();

        Page<ProjectDTO> dtoPage = new PageImpl<>(
                projectDTOS,
                PageRequest.of(page, size),
                totalElements
        );
        return ResponseEntity.ok(dtoPage);
    }


    @GetMapping("/user/{username}")
    public ResponseEntity<?> getProjectsByUserPaged(
            @PathVariable String username,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        Page<Project> projects = pagingProjectService.getProjectsByUserPaged(user, page, size);
        return ResponseEntity.ok(projects);
    }
}
