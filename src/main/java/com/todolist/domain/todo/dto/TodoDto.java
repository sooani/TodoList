package com.todolist.domain.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class TodoDto {
    @Getter
    @AllArgsConstructor
    public static class Post {
        @NotBlank(message = "내용은 공백이 아니어야 합니다.")
        private String title;
        @Positive
        @NotNull(message = "순서는 공백이 아니어야 합니다.")
        private Integer todoOrder;
        @NotNull
        private boolean completed;
    }

    @Getter
    @Setter
    @Builder
    public static class Patch {
        private long todoId;
        @NotBlank(message = "내용은 공백이 아니어야 합니다.")
        private String title;
        @Positive
        @NotNull(message = "순서는 공백이 아니어야 합니다.")
        private Integer todoOrder;
        @NotNull
        private boolean completed;
    }

    @AllArgsConstructor
    @Builder
    @Getter
    public static class Response {
        private long todoId;
        private String title;
        private Integer todoOrder;
        private boolean completed;
    }
}
