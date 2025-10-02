package com.example.realtimetaskmanagement.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Project {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String Description;

    @CreationTimestamp
    private LocalDateTime createdAt;


    private LocalDateTime endDate;

    @ManyToOne
    @JoinColumn(name = "createdBy")
    private Users createdBy;

    @OneToMany(mappedBy = "project",cascade = CascadeType.ALL ,orphanRemoval = true)
    private List<ProjectMembers> members = new ArrayList<>();

    @OneToMany
    private List<Task> tasks = new ArrayList<>();


}
