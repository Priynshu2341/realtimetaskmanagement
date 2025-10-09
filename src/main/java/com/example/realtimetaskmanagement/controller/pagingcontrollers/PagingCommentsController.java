package com.example.realtimetaskmanagement.controller.pagingcontrollers;

import com.example.realtimetaskmanagement.dto.responsedto.CommentResponseDTO;
import com.example.realtimetaskmanagement.entity.Comment;
import com.example.realtimetaskmanagement.service.pagingservice.PagingCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/paging/comments")
@RequiredArgsConstructor
public class PagingCommentsController {

    private final PagingCommentService commentService;

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findCommentsByTaskPaged(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<Comment> comments = commentService.getAllCommentForTaskPaged(id, page, size);
        List<CommentResponseDTO> commentResponseDTO = comments.getContent().stream().map(
                comment -> new CommentResponseDTO(
                        comment.getId(), comment.getContent(), comment.getUsers().getUsername(), comment.getCreatedAt().toString()
                )
        ).toList();

        Page<CommentResponseDTO> page1 = new PageImpl<>(commentResponseDTO, comments.getPageable(), comments.getTotalElements());
        return ResponseEntity.ok(page1);
    }
}
