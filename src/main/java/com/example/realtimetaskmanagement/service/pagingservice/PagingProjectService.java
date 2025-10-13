package com.example.realtimetaskmanagement.service.pagingservice;

import com.example.realtimetaskmanagement.entity.Project;
import com.example.realtimetaskmanagement.entity.Users;
import com.example.realtimetaskmanagement.reps.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PagingProjectService {

    private final ProjectRepository projectRepository;


    @Cacheable(value = "projectsPaged", key = "#page + '-' + #size ")
    public List<Project> getAllProjectPaged(int page, int size) {
        Page<Project> projectsPaged = projectRepository.findAll(PageRequest.of(page, size));
        return projectsPaged.getContent();
    }

    public Page<Project> getProjectsByUserPaged(Users user, int page, int size) {
        return projectRepository.findByCreatedBy(user, PageRequest.of(page, size));

    }
}
