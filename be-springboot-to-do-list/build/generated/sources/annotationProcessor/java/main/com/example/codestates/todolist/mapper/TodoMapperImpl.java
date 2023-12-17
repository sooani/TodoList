package com.example.codestates.todolist.mapper;

import com.example.codestates.todolist.dto.TodoPatchDto;
import com.example.codestates.todolist.dto.TodoPostDto;
import com.example.codestates.todolist.dto.TodoResponseDto;
import com.example.codestates.todolist.entity.Todo;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-12-17T00:09:13+0900",
    comments = "version: 1.5.1.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.6.1.jar, environment: Java 11.0.20.1 (Azul Systems, Inc.)"
)
@Component
public class TodoMapperImpl implements TodoMapper {

    @Override
    public Todo todoPostDtoToTodo(TodoPostDto todoPostDto) {
        if ( todoPostDto == null ) {
            return null;
        }

        Todo todo = new Todo();

        todo.setTodoId( todoPostDto.getTodoId() );
        todo.setTitle( todoPostDto.getTitle() );
        todo.setTodoOrder( todoPostDto.getTodoOrder() );
        todo.setCompleted( todoPostDto.isCompleted() );

        return todo;
    }

    @Override
    public Todo todoPatchDtoToTodo(TodoPatchDto todoPatchDto) {
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
    public TodoResponseDto todoToTodoResponseDto(Todo todo) {
        if ( todo == null ) {
            return null;
        }

        TodoResponseDto.TodoResponseDtoBuilder todoResponseDto = TodoResponseDto.builder();

        if ( todo.getTodoId() != null ) {
            todoResponseDto.todoId( todo.getTodoId() );
        }
        todoResponseDto.title( todo.getTitle() );
        if ( todo.getTodoOrder() != null ) {
            todoResponseDto.todoOrder( String.valueOf( todo.getTodoOrder() ) );
        }
        todoResponseDto.completed( todo.isCompleted() );

        return todoResponseDto.build();
    }
}
