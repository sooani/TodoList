package com.example.codestates.todolist.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
public class TodoPostDto {
    private long todoId;
    @NotBlank
    private String title;
    @Positive
    private Integer todoOrder;
    @NotNull
    private boolean completed;

}
