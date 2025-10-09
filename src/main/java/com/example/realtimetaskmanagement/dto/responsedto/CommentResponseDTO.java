package com.example.realtimetaskmanagement.dto.responsedto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CommentResponseDTO {
    private Long id;
    private String content;
    private String username;
    private String createdAt;
}
