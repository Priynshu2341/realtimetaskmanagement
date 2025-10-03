package com.example.realtimetaskmanagement.reps;

import com.example.realtimetaskmanagement.entity.Project;
import com.example.realtimetaskmanagement.entity.ProjectMembers;
import com.example.realtimetaskmanagement.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectMemberRep extends JpaRepository<ProjectMembers,Long> {

    List<ProjectMembers> findByProject(Project project);
    Optional<ProjectMembers> findByProjectAndUsers(Project project, Users users);

}
