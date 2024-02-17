package com.todolist.global.exception;

import lombok.Getter;

public enum ExceptionCode {
    TODO_NOT_FOUND(404, "Todo Not Found"),
    MEMBER_NOT_FOUND(404, "Member Not Found"),
    MEMBER_EXISTS(409, "Member exists");

    @Getter
    private int status;

    @Getter
    private String message;

    ExceptionCode(int status, String message) {
        this.status = status;
        this.message = message;
    }

}
