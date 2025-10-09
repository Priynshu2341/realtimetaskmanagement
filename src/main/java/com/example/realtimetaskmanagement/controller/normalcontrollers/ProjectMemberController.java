package com.example.realtimetaskmanagement.controller.normalcontrollers;

import com.example.realtimetaskmanagement.dto.responsedto.ProjectMemberDTO;
import com.example.realtimetaskmanagement.entity.Project;
import com.example.realtimetaskmanagement.entity.ProjectMembers;
import com.example.realtimetaskmanagement.entity.Users;
import com.example.realtimetaskmanagement.reps.UserRepository;
import com.example.realtimetaskmanagement.service.normalservices.ProjectMemberService;
import com.example.realtimetaskmanagement.service.normalservices.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/projectMember")
@RequiredArgsConstructor
public class ProjectMemberController {

    private final ProjectMemberService projectMemberService;
    private final ProjectService projectService;
    private final UserRepository userRepository;

    @PostMapping("/add/{projectId}")
    public ResponseEntity<?> addMemberToProject(@PathVariable Long projectId,@RequestBody ProjectMemberDTO projectMemberDTO){
        Users users = userRepository.findByUsername(projectMemberDTO.getUsername()).orElseThrow(()-> new UsernameNotFoundException("Invalid Username"));
        Project project = projectService.getProjectById(projectId).orElseThrow(()-> new RuntimeException("Invalid Project"));
        ProjectMembers projectMembers = projectMemberService.addMember(projectId,projectMemberDTO.getUsername());
        projectMembers.setUsers(users);
        projectMembers.setProject(project);
        return ResponseEntity.ok(Map.of(
                "message","User added to Project",
                "User",new ProjectMemberDTO(projectMembers.getUsers().getUsername())
        ));
    }

    @GetMapping("/getMemberByProject/{id}")
    public ResponseEntity<?> getMemberByProjectId(@PathVariable Long id){
        List<ProjectMembers> projectMembers = projectMemberService.listMembers(id);
        return ResponseEntity.ok(projectMembers);
    }

    @DeleteMapping("/removeProjectMember/{id}")
    public ResponseEntity<?> removeByProjectId(@PathVariable Long id, @RequestParam String username) {
        projectMemberService.removeProjectMember(id, username);
        return ResponseEntity.ok("Member Removed From Project");
    }
}
