package com.example.realtimetaskmanagement.service;

import com.example.realtimetaskmanagement.entity.Project;
import com.example.realtimetaskmanagement.entity.Task;
import com.example.realtimetaskmanagement.entity.Users;
import com.example.realtimetaskmanagement.reps.TaskRepository;
import com.example.realtimetaskmanagement.reps.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final ProjectService projectService;

    public Task createTask(Long projectId, Task task, String assigneeUsername) {
        Project project = projectService.getProjectById(projectId).orElseThrow(() -> new RuntimeException("Invalid Project Id"));
        Users userAssignee = userRepository.findByUsername(assigneeUsername).orElseThrow(() -> new RuntimeException("Invalid Username"));
        task.setAssignee(userAssignee);
        task.setProject(project);
        return taskRepository.save(task);
    }

    public List<Task> findByAssignee(Users users) {
        return taskRepository.findByAssignee(users);
    }

    public List<Task> getTaskByProject(Long projectId) {
        return taskRepository.findByProjectId(projectId);
    }

    public Task updateTaskStatus(Long taskId, Task.Status status) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Invalid Id"));
        task.setStatus(status);
        return taskRepository.save(task);
    }

    public Task updateTaskPriority(Long taskId, Task.Priority priority) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Invalid Id"));
        task.setPriority(priority);
        return taskRepository.save(task);
    }

    public void deleteTask(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Invalid Task Id"));
        taskRepository.delete(task);
    }



}
