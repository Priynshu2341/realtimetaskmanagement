package com.example.realtimetaskmanagement.reps;

import com.example.realtimetaskmanagement.entity.ProjectMembers;
import com.example.realtimetaskmanagement.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectMemberRep extends JpaRepository<ProjectMembers,Long> {

    List<ProjectMembers> findByUsers(Users users);
}
