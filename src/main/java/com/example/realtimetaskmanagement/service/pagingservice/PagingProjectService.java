package com.example.realtimetaskmanagement.service.pagingservice;

import com.example.realtimetaskmanagement.entity.Project;
import com.example.realtimetaskmanagement.entity.Users;
import com.example.realtimetaskmanagement.reps.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PagingProjectService {

       private final ProjectRepository projectRepository;


    public Page<Project> getAllProjectPaged(int page, int size){
        return projectRepository.findAll(PageRequest.of(page, size));
    }

    public Page<Project> getProjectsByUserPaged(Users user, int page, int size) {
        return projectRepository.findByCreatedBy(user, PageRequest.of(page, size));

    }
}
