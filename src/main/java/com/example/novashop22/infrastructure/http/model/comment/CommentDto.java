package com.example.novashop22.infrastructure.http.model.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private String id;
    private String userId;
    private LocalDateTime time;
    private String taskId;
    private String processInstanceId;
    private String type;
    private String fullMessage;
}