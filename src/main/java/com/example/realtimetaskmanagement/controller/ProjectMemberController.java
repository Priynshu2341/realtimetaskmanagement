package com.example.realtimetaskmanagement.controller;

import com.example.realtimetaskmanagement.dto.ProjectMemberDTO;
import com.example.realtimetaskmanagement.entity.Project;
import com.example.realtimetaskmanagement.entity.ProjectMembers;
import com.example.realtimetaskmanagement.reps.ProjectMemberRep;
import com.example.realtimetaskmanagement.service.ProjectMemberService;
import com.example.realtimetaskmanagement.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/projectMember")
@RequiredArgsConstructor
public class ProjectMemberController {

    private final ProjectMemberService projectMemberService;
    private final ProjectService projectService;

    @PostMapping("/add/{projectId}")
    public ResponseEntity<?> addMemberToProject(@PathVariable Long projectId,@RequestBody ProjectMemberDTO projectMemberDTO){
        ProjectMembers projectMembers = projectMemberService.addMember(projectId,projectMemberDTO.getUsername(),projectMemberDTO.getRoleType());
        return ResponseEntity.ok(Map.of(
                "message","User added to Project",
                "User", projectMembers
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
