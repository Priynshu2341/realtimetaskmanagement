package com.example.realtimetaskmanagement.reps;

import com.example.realtimetaskmanagement.entity.Project;
import com.example.realtimetaskmanagement.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project,Long> {

    Page<Project> findByCreatedBy(Users user, Pageable pageable);

}
