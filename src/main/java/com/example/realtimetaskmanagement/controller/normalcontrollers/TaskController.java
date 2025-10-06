package com.example.realtimetaskmanagement.controller.normalcontrollers;


import com.example.realtimetaskmanagement.dto.TaskDTO;
import com.example.realtimetaskmanagement.dto.TaskReqDTO;
import com.example.realtimetaskmanagement.entity.Project;
import com.example.realtimetaskmanagement.entity.Task;
import com.example.realtimetaskmanagement.entity.Users;
import com.example.realtimetaskmanagement.reps.TaskRepository;
import com.example.realtimetaskmanagement.reps.UserRepository;
import com.example.realtimetaskmanagement.service.normalservices.ProjectService;
import com.example.realtimetaskmanagement.service.normalservices.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final UserRepository userRepository;
    private final ProjectService projectService;
    private final TaskRepository taskRepository;


    @PostMapping("/create/{projectId}")
    public ResponseEntity<?> createTask(@PathVariable Long projectId, @RequestBody TaskReqDTO task) {
        Task task1 = new Task();
        task1.setDescription(task.getDescription());
        task1.setTitle(task.getTitle());
        Task savedTask = taskService.createTask(projectId, task1, task.getUsername());
        return ResponseEntity.ok(savedTask);
    }

    @PutMapping("/assignTask/{id}")
    public ResponseEntity<?> AssignTaskToUser(@PathVariable Long id, @RequestParam String username) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Invalid Task id"));
        Users users = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Invalid Username"));
        task.setAssignee(users);
        taskRepository.save(task);
        return ResponseEntity.ok(Map.of(
                "message", "task assigned To user ",
                "task", task
        ));
    }

    @GetMapping("/findByUser")
    public ResponseEntity<?> findTaskByUser(@RequestBody String username) {
        Users users = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User Not Found"));
        List<Task> tasks = taskService.findByAssignee(users);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/findByProjectId/{projectId}")
    public ResponseEntity<?> findTaskByProjectId(@PathVariable Long projectId) {
        Project project = projectService.getProjectById(projectId).orElseThrow(() -> new RuntimeException("Invalid Project ID"));
        List<Task> tasks = taskService.getTaskByProject(project.getId());
        return ResponseEntity.ok(tasks);
    }

    @PutMapping("/updateTaskStatus/{taskId}")
    public ResponseEntity<?> updateTaskStatus(@PathVariable Long taskId, @RequestParam Task.Status status) {
        Task updatedTask = taskService.updateTaskStatus(taskId, status);
        return ResponseEntity.ok(Map.of(
                        "message", "Task Status Updated to" + " " + status.toString(),
                        "task", updatedTask
                )
        );
    }

    @PutMapping("/updateTaskPriority/{taskId}")
    public ResponseEntity<?> updateTaskPriorityById(@PathVariable Long taskId, @RequestParam Task.Priority priority) {
        Task updatedTask = taskService.updateTaskPriority(taskId, priority);
        return ResponseEntity.ok(Map.of(
                        "message", "Task Status Updated to" + " " + priority.toString(),
                        "task", updatedTask
                )
        );
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.ok(Map.of("message", "Task deleted successfully"));
    }

    @GetMapping("/filter")
    public ResponseEntity<?> filterTaskByStatusAndPriorityAndUser(
            @RequestParam(required = false) Task.Status status,
            @RequestParam(required = false) Task.Priority priority,
            @RequestParam(required = false) String username
    ){
        List<Task> tasks = taskService.filter(status,priority,username);
        return ResponseEntity.ok(tasks);
    }

}
