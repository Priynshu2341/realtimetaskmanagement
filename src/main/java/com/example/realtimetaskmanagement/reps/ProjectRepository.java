package com.example.realtimetaskmanagement.reps;

import com.example.realtimetaskmanagement.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project,Long> {


}
