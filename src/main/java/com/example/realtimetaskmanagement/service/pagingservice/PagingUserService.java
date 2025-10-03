package com.example.realtimetaskmanagement.service.pagingservice;

import com.example.realtimetaskmanagement.entity.Users;
import com.example.realtimetaskmanagement.reps.UserRepository;
import jakarta.persistence.GeneratedValue;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PagingUserService {

    private final UserRepository userRepository;


    public Page<Users> findAllUsersPaged(int page,int size){
        return userRepository.findAll(PageRequest.of(page, size));
    }
}
