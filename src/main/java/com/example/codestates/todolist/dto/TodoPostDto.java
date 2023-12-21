package com.example.codestates.todolist.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Getter
@AllArgsConstructor
public class TodoPostDto {
    @NotBlank
    private String title;
    @Positive
    private Integer todoOrder;

}
