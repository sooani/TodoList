package com.example.codestates.todolist.mapper;

import com.example.codestates.todolist.dto.TodoPatchDto;
import com.example.codestates.todolist.dto.TodoPostDto;
import com.example.codestates.todolist.dto.TodoResponseDto;
import com.example.codestates.todolist.entity.Todo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TodoMapper {

    Todo todoPostDtoToTodo(TodoPostDto todoPostDto);

    Todo todoPatchDtoToTodo(TodoPatchDto todoPatchDto);

    TodoResponseDto todoToTodoResponseDto(Todo todo);
}
