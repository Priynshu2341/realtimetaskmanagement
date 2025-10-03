package com.example.realtimetaskmanagement.reps;

import com.example.realtimetaskmanagement.entity.Task;
import com.example.realtimetaskmanagement.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByAssignee(Users users);

    List<Task> findByProjectId(Long id);

    List<Task> findByPriority(Task.Priority priority);

    List<Task> findByStatus(Task.Status status);

    Page<Task> findByAssignee(Users users, Pageable pageable);

    Page<Task> findByProjectId(Long id, Pageable pageable);

    Page<Task> findByPriority(Task.Priority priority, Pageable pageable);

    Page<Task> findByStatus(Task.Status status, Pageable pageable);

}
