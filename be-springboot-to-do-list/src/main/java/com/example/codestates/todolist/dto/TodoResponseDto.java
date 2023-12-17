package com.example.codestates.todolist.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TodoResponseDto {
    private long todoId;
    private String title;
    private String todoOrder;
    private boolean completed;

}
