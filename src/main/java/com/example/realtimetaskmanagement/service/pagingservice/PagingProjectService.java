package com.example.realtimetaskmanagement.service.pagingservice;

import com.example.realtimetaskmanagement.dto.responsedto.ProjectDTO;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PagingProjectService {

    private final ProjectRepository projectRepository;


    @Cacheable(value = "projectsPaged", key = "#page + '-' + #size")
    public List<ProjectDTO> getAllProjectPaged(int page, int size) {
        Page<Project> projectsPaged = projectRepository.findAll(PageRequest.of(page, size));

        // Convert each Project to a ProjectDTO
        return projectsPaged.getContent().stream().map(project -> {
            // Extract all member usernames
            List<String> memberUsernames = project.getMembers().stream()
                    .map(member -> member.getUsers().getUsername()) // adjust if entity structure differs
                    .collect(Collectors.toList());

            return new ProjectDTO(
                    project.getId(),
                    project.getTitle(),
                    project.getCreatedAt(),
                    project.getEndDate(),
                    project.getDescription(),
                    project.getCreatedBy().getUsername(),
                    memberUsernames
            );
        }).collect(Collectors.toList());
    }

    public Page<Project> getProjectsByUserPaged(Users user, int page, int size) {
        return projectRepository.findByCreatedBy(user, PageRequest.of(page, size));

    }
}
