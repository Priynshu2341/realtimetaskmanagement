package com.example.realtimetaskmanagement.service.pagingservice;

import com.example.realtimetaskmanagement.dto.responsedto.TaskDTO;
import com.example.realtimetaskmanagement.entity.Task;
import com.example.realtimetaskmanagement.entity.Users;
import com.example.realtimetaskmanagement.reps.TaskRepository;
import com.example.realtimetaskmanagement.reps.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PagingTaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public Page<Task> findByAssignee(Users users, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return taskRepository.findByAssignee(users, pageable);
    }

    @Cacheable(value = "taskPaged", key = "#projectId + '-' + #page + '-' + #size")
    public List<TaskDTO> getTaskByProject(Long projectId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Task> tasks = taskRepository.findByProjectId(projectId, pageable);
        return tasks.stream().map(
                task -> new TaskDTO(
                        task.getId(),
                        task.getTitle(),
                        task.getDescription(),
                        task.getPriority().toString(),
                        task.getStatus().toString(),
                        task.getDueDate(),
                        task.getAssignee() != null ? task.getAssignee().getUsername() : null,
                        task.getProject() != null ? task.getProject().getId() : null
                )
        ).toList();
    }

    public Page<Task> findByPriority(Task.Priority priority, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return taskRepository.findByPriority(priority, pageable);
    }

    public Page<Task> findByStatus(Task.Status status, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return taskRepository.findByStatus(status, pageable);
    }

    public Page<Task> filter(Task.Status status, Task.Priority priority, String username, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        if (status != null) {
            return taskRepository.findByStatus(status, pageable);
        }
        if (priority != null) {
            return taskRepository.findByPriority(priority, pageable);
        }
        if (username != null) {
            Users user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            return taskRepository.findByAssignee(user, pageable);
        }
        return taskRepository.findAll(pageable);
    }

}
