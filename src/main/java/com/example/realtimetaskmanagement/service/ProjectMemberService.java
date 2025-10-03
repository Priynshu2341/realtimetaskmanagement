package com.example.realtimetaskmanagement.service;

import com.example.realtimetaskmanagement.entity.Project;
import com.example.realtimetaskmanagement.entity.ProjectMembers;
import com.example.realtimetaskmanagement.entity.RoleType;
import com.example.realtimetaskmanagement.entity.Users;
import com.example.realtimetaskmanagement.reps.ProjectMemberRep;
import com.example.realtimetaskmanagement.reps.ProjectRepository;
import com.example.realtimetaskmanagement.reps.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectMemberService {

    private final UserRepository userRepository;
    private final ProjectMemberRep projectMemberRep;
    private final ProjectRepository projectRepository;

    public ProjectMembers addMember(Long projectId, String username, RoleType roleType){
        Project project = projectRepository.findById(projectId).orElseThrow(()-> new RuntimeException("Invalid Project Id"));
        Users users = userRepository.findByUsername(username).orElseThrow(()-> new RuntimeException("Invalid Username"));
        projectMemberRep.findByProjectAndUsers(project,users).ifPresent(pm-> {throw new RuntimeException("User Already in Project"); });
        ProjectMembers projectMembers = new ProjectMembers();
        projectMembers.setUsers(users);
        projectMembers.setProject(project);
        projectMembers.setRoleType(roleType);
        return projectMemberRep.save(projectMembers);
    }

    public void removeProjectMember(Long projectId, String username){
        Project project = projectRepository.findById(projectId).orElseThrow(()-> new RuntimeException("Invalid Project Id"));
        Users users = userRepository.findByUsername(username).orElseThrow(()-> new RuntimeException("Invalid Username"));
        ProjectMembers projectMembers = projectMemberRep.findByProjectAndUsers(project,users).orElseThrow(()-> new RuntimeException("User Not Found in Project"));
        projectMemberRep.delete(projectMembers);
    }

    public List<ProjectMembers> listMembers(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        return projectMemberRep.findByProject(project);
    }
}
