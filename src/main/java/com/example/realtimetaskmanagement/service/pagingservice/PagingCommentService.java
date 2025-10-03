package com.example.realtimetaskmanagement.service.pagingservice;

import com.example.realtimetaskmanagement.entity.Comment;
import com.example.realtimetaskmanagement.entity.Task;
import com.example.realtimetaskmanagement.reps.CommentRepository;
import com.example.realtimetaskmanagement.reps.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PagingCommentService {


    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;


    public Page<Comment> getAllCommentForTaskPaged(Long id,int page,int size){
     Task task = taskRepository.findById(id).orElseThrow(()-> new RuntimeException("Invalid Comment ID"));
        return commentRepository.findCommentsByTask(task, PageRequest.of(page,size));
    }
}
