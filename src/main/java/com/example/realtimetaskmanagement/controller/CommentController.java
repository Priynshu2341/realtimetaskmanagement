package com.example.realtimetaskmanagement.controller;

import com.example.realtimetaskmanagement.dto.CommentDTO;
import com.example.realtimetaskmanagement.dto.CommentResponseDTO;
import com.example.realtimetaskmanagement.entity.Comment;
import com.example.realtimetaskmanagement.entity.Users;
import com.example.realtimetaskmanagement.reps.CommentRepository;
import com.example.realtimetaskmanagement.reps.UserRepository;
import com.example.realtimetaskmanagement.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {


    private final CommentService commentService;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @PostMapping("/create/{taskId}")
    public ResponseEntity<?> createComment(@PathVariable Long taskId, @RequestBody CommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setContent(commentDTO.getContent());
        Comment savedComment = commentService.addCommentByUser(taskId, comment, commentDTO.getUsername());
        return ResponseEntity.ok(Map.of(
                "message", "Comment Saved",
                "Comment", new CommentResponseDTO(savedComment.getContent(), savedComment.getUsers().getUsername())
        ));
    }

    @GetMapping("/getComments/{id}")
    public ResponseEntity<?> getCommentsByTask(@PathVariable Long id) {
        List<Comment> comments = commentService.getAllCommentForTask(id);
        List<CommentResponseDTO> responseDTOS = comments.stream().map(
                comment -> new CommentResponseDTO(
                        comment.getContent(),
                        comment.getUsers().getUsername()
                )
        ).toList();
        return ResponseEntity.ok(responseDTOS);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id, @RequestParam String username) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new RuntimeException("Comment Not Found"));
        Users users = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User Not Found"));
        if (!comment.getUsers().getId().equals(users.getId())) {
           return ResponseEntity.status(403).body("You are not allowed to delete this comment");
        }
        commentRepository.deleteCommentByIdAndUsers(id, users);
        return ResponseEntity.ok("Comment Deleted Successfully");
    }


}
