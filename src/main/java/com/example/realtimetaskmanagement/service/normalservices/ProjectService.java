package com.example.realtimetaskmanagement.service.normalservices;

import com.example.realtimetaskmanagement.entity.Project;
import com.example.realtimetaskmanagement.reps.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;


    public Project createProject(Project project){
        return projectRepository.save(project);
    }

    public List<Project> getAllProject(){
        return projectRepository.findAll();
    }

    public Optional<Project> getProjectById(Long id){
        return projectRepository.findById(id);
    }

    public void deleteProjectByID(Long projectId){
        Project project =projectRepository.findById(projectId).orElseThrow(()-> new RuntimeException("Invalid Project Id"));
        projectRepository.delete(project);
    }
}
