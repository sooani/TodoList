package com.example.codestates.todolist.mapper;

import com.todolist.domain.todo.dto.TodoDto;
import com.todolist.domain.todo.entity.Todo;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

import com.todolist.domain.todo.mapper.TodoMapper;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-30T02:34:15+0900",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.6.1.jar, environment: Java 11.0.20.1 (Azul Systems, Inc.)"
)
@Component
public class TodoMapperImpl implements TodoMapper {

    @Override
    public Todo todoDtoPostToTodo(TodoDto.Post todoPostDto) {
        if ( todoPostDto == null ) {
            return null;
        }

        Todo todo = new Todo();

        todo.setTitle( todoPostDto.getTitle() );
        todo.setTodoOrder( todoPostDto.getTodoOrder() );
        todo.setCompleted( todoPostDto.isCompleted() );

        return todo;
    }

    @Override
    public Todo todoDtoPatchToTodo(TodoDto.Patch todoPatchDto) {
        if ( todoPatchDto == null ) {
            return null;
        }

        Todo todo = new Todo();

        todo.setTodoId( todoPatchDto.getTodoId() );
        todo.setTitle( todoPatchDto.getTitle() );
        todo.setTodoOrder( todoPatchDto.getTodoOrder() );
        todo.setCompleted( todoPatchDto.isCompleted() );

        return todo;
    }

    @Override
    public TodoDto.Response todoToTodoDtoResponse(Todo todo) {
        if ( todo == null ) {
            return null;
        }

        TodoDto.Response.ResponseBuilder response = TodoDto.Response.builder();

        if ( todo.getTodoId() != null ) {
            response.todoId( todo.getTodoId() );
        }
        response.title( todo.getTitle() );
        response.todoOrder( todo.getTodoOrder() );
        if ( todo.getCompleted() != null ) {
            response.completed( todo.getCompleted() );
        }

        return response.build();
    }

    @Override
    public List<TodoDto.Response> todosToTodoDtoResponses(List<Todo> todos) {
        if ( todos == null ) {
            return null;
        }

        List<TodoDto.Response> list = new ArrayList<TodoDto.Response>( todos.size() );
        for ( Todo todo : todos ) {
            list.add( todoToTodoDtoResponse( todo ) );
        }

        return list;
    }
}
