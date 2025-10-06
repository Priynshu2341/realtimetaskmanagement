package com.example.realtimetaskmanagement.controller.pagingcontrollers;

import com.example.realtimetaskmanagement.dto.ProjectDTO;
import com.example.realtimetaskmanagement.entity.Project;
import com.example.realtimetaskmanagement.entity.Users;
import com.example.realtimetaskmanagement.reps.UserRepository;
import com.example.realtimetaskmanagement.service.pagingservice.PagingProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/paging/project")
@RequiredArgsConstructor
public class PagingProjectController {

    private final PagingProjectService pagingProjectService;
    private final UserRepository userRepository;


    @GetMapping("/all")
    public ResponseEntity<?> getAllProjectsPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<Project> projects = pagingProjectService.getAllProjectPaged(page, size);
        List<ProjectDTO> projectDTOS = projects.getContent().stream().map(
                project -> new ProjectDTO(
                        project.getId(),
                        project.getTitle(),
                        project.getCreatedAt(),
                        project.getEndDate(),
                        project.getDescription(),
                        project.getCreatedBy().getUsername()
                )
        ).toList();
        Page<ProjectDTO> dtoPage = new PageImpl<>(
                projectDTOS,
                projects.getPageable(),
                projects.getTotalElements()
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
