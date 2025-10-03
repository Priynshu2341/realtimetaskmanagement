package com.example.realtimetaskmanagement.reps;

import com.example.realtimetaskmanagement.entity.Comment;
import com.example.realtimetaskmanagement.entity.Task;
import com.example.realtimetaskmanagement.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    List<Comment> findCommentByTask(Task task);
    void deleteCommentByIdAndUsers(Long id, Users users);
}
