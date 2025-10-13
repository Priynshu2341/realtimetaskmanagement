package com.example.realtimetaskmanagement.dto.responsedto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDTO {

    private String type;
    private String message;
    private Long projectId;
    private Long TaskId;

}
