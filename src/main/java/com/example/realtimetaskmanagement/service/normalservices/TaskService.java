package com.example.realtimetaskmanagement.service.normalservices;

import com.example.realtimetaskmanagement.entity.Project;
import com.example.realtimetaskmanagement.entity.Task;
import com.example.realtimetaskmanagement.entity.Users;
import com.example.realtimetaskmanagement.reps.ProjectRepository;
import com.example.realtimetaskmanagement.reps.TaskRepository;
import com.example.realtimetaskmanagement.reps.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final ProjectService projectService;
    private final RedisTemplate<String, Object> redisTemplate;
    private final ProjectRepository projectRepository;

    public void clearTaskCacheForProject(Long projectId) {
        String hashKey = "taskPaged";
        Set<Object> fields = redisTemplate.opsForHash().keys(hashKey);
        List<Object> fieldsToDelete = fields.stream()
                .filter(f -> f.toString().startsWith(projectId + "-"))
                .toList();
        if (!fieldsToDelete.isEmpty()) {
            redisTemplate.opsForHash().delete(hashKey, fieldsToDelete.toArray());
        }
    }

    public Task createTask(Long projectId, Task task, String assigneeUsername) {
        Project project = projectService.getProjectById(projectId).orElseThrow(() -> new RuntimeException("Invalid Project Id"));
        Users userAssignee = userRepository.findByUsername(assigneeUsername).orElseThrow(() -> new RuntimeException("Invalid Username"));
        task.setAssignee(userAssignee);
        task.setProject(project);
        clearTaskCacheForProject(projectId);
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

    public List<Task> filter(Task.Status status, Task.Priority priority, String username) {
        if (status != null) {
            return taskRepository.findByStatus(status);
        }
        if (priority != null) {
            return taskRepository.findByPriority(priority);
        }
        if (username != null) {
            Users user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            return taskRepository.findByAssignee(user);
        }
        return taskRepository.findAll();
    }


}
