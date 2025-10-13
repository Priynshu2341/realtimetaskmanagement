package com.example.realtimetaskmanagement.reps;

import com.example.realtimetaskmanagement.entity.Project;
import com.example.realtimetaskmanagement.entity.Task;
import com.example.realtimetaskmanagement.entity.Users;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    Page<Project> findByCreatedBy(Users user, Pageable pageable);

    @Query("SELECT p FROM Project p JOIN p.tasks t WHERE t.id = :taskId")
    Optional<Project> findProjectByTaskId(@Param("taskId") Long taskId);


}
