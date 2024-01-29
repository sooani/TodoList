package com.example.codestates.todolist.mapper;

import com.example.codestates.todolist.dto.TodoDto;
import com.example.codestates.todolist.entity.Todo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TodoMapper {

    Todo todoDtoPostToTodo(TodoDto.Post todoPostDto);

    Todo todoDtoPatchToTodo(TodoDto.Patch todoPatchDto);

    TodoDto.Response todoToTodoDtoResponse(Todo todo);

    List<TodoDto.Response> todosToTodoDtoResponses(List<Todo> todos); //페이지적용
}
