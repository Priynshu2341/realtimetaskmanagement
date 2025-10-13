package com.example.realtimetaskmanagement.controller.pagingcontrollers;

import com.example.realtimetaskmanagement.dto.responsedto.TaskDTO;
import com.example.realtimetaskmanagement.entity.Project;
import com.example.realtimetaskmanagement.entity.Task;
import com.example.realtimetaskmanagement.entity.Users;
import com.example.realtimetaskmanagement.reps.TaskRepository;
import com.example.realtimetaskmanagement.reps.UserRepository;
import com.example.realtimetaskmanagement.service.normalservices.ProjectService;
import com.example.realtimetaskmanagement.service.pagingservice.PagingTaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pagingTask")
@RequiredArgsConstructor
public class PagingTaskController {

    private final PagingTaskService taskService;
    private final UserRepository userRepository;
    private final ProjectService projectService;
    private final TaskRepository taskRepository;


    @GetMapping("/findByUserPaged")
    public ResponseEntity<?> findTaskByUserPaged(
            @RequestParam String username,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Users users = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User Not Found"));
        Page<Task> tasks = taskService.findByAssignee(users, page, size);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/findByProjectIdPaged/{projectId}")
    public ResponseEntity<?> findTaskByProjectIdPaged(
            @PathVariable Long projectId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Project project = projectService.getProjectById(projectId)
                .orElseThrow(() -> new RuntimeException("Invalid Project ID"));
        List<TaskDTO> tasks = taskService.getTaskByProject(project.getId(), page, size);
        long totalElements = taskRepository.count();
        Page<TaskDTO> taskDTOS1 = new PageImpl<>(
                tasks,
                PageRequest.of(page, size),
                totalElements
        );

        return ResponseEntity.ok(taskDTOS1);
    }

    @GetMapping("/filterPaged")
    public ResponseEntity<?> filterTaskByStatusPriorityUserPaged(
            @RequestParam(required = false) Task.Status status,
            @RequestParam(required = false) Task.Priority priority,
            @RequestParam(required = false) String username,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<Task> tasks = taskService.filter(status, priority, username, page, size);
        return ResponseEntity.ok(tasks);
    }
}
