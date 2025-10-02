package com.example.realtimetaskmanagement.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Data
@Entity(name = "users")
public class Users implements UserDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String username;

    @Column(nullable = false,unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(value = EnumType.STRING)
    private RoleType roleType = RoleType.USER;

    private String refreshToken;

    @OneToMany(mappedBy = "createdBy")
    private List<Project> projectsCreated = new ArrayList<>();

    @OneToMany(mappedBy = "assignee")
    private List<Task> tasksAssigned = new ArrayList<>();

    @OneToMany(mappedBy = "users",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProjectMembers> memberships = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + roleType.name()));
    }
}
