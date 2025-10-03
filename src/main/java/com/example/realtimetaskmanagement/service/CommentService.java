package com.example.realtimetaskmanagement.service;

import com.example.realtimetaskmanagement.entity.Comment;
import com.example.realtimetaskmanagement.entity.Task;
import com.example.realtimetaskmanagement.entity.Users;
import com.example.realtimetaskmanagement.reps.CommentRepository;
import com.example.realtimetaskmanagement.reps.TaskRepository;
import com.example.realtimetaskmanagement.reps.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskService;

    public Comment addCommentByUser(Long taskId,Comment comment,String username){
        Users user = userRepository.findByUsername(username).orElseThrow(()-> new RuntimeException("User NOt Found"));
        Task task = taskService.findById(taskId).orElseThrow(()->new RuntimeException("Invalid Id"));
        comment.setUsers(user);
        comment.setTask(task);
        return commentRepository.save(comment);
    }

    public List<Comment> getAllCommentForTask(Long taskID){
        Task task = taskService.findById(taskID).orElseThrow(()-> new RuntimeException("Invalid Task ID"));
         return commentRepository.findCommentByTask(task);
    }
}
