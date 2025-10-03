package com.example.realtimetaskmanagement.controller.pagingcontrollers;

import com.example.realtimetaskmanagement.entity.Comment;
import com.example.realtimetaskmanagement.service.pagingservice.PagingCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    ){
        Page<Comment> comments =commentService.getAllCommentForTaskPaged(id, page, size);
        return ResponseEntity.ok(comments);
    }
}
