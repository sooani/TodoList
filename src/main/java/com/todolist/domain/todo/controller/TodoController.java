package com.todolist.domain.todo.controller;

import com.todolist.domain.todo.dto.TodoDto;
import com.todolist.domain.todo.entity.Todo;
import com.todolist.domain.todo.mapper.TodoMapper;
import com.todolist.domain.todo.service.TodoService;
import com.todolist.global.utils.UriCreator;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/todos")
public class TodoController {
    private final static String TODO_DEFAULT_URL = "/v1/todos";
    private final TodoService todoService;
    private final TodoMapper todoMapper;

    public TodoController(TodoService todoService, TodoMapper todoMapper) {
        this.todoService = todoService;
        this.todoMapper = todoMapper;
    }

    @PostMapping
    public ResponseEntity postTodo(@Valid @RequestBody TodoDto.Post todoPostDto) {
        Todo todo = todoMapper.todoDtoPostToTodo(todoPostDto);
        Todo savedTodo = todoService.createTodo(todo);
        URI location = UriCreator.creatreUri(TODO_DEFAULT_URL, savedTodo.getTodoId());

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{todo-id}")
    public ResponseEntity getTodo(@PathVariable("todo-id") @Positive long todoId) {
        Todo todo = todoService.findTodo(todoId);
        return new ResponseEntity<>(todo, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getTodos(@Positive @RequestParam int page,
                                   @Positive @RequestParam int size) {

        Page<Todo> pageTodos = todoService.findTodos(page - 1, size);
        List<Todo> todos = pageTodos.getContent();
        return new ResponseEntity<>(
                todoMapper.todosToTodoDtoResponses(todos), HttpStatus.OK);
    }

    @PatchMapping("/{todo-id}")
    public ResponseEntity patchTodo(@PathVariable("todo-id") @Positive long todoId,
                                    @Valid @RequestBody TodoDto.Patch todoPatchDto) {
        Todo todo = todoMapper.todoDtoPatchToTodo(todoPatchDto);
        Todo updatedTodo = todoService.patchTodo(todo);
        return new ResponseEntity<>(todoMapper.todoToTodoDtoResponse(updatedTodo), HttpStatus.OK);
    }

    @DeleteMapping("/{todo-id}")
    public ResponseEntity deleteTodo(@PathVariable("todo-id") @Positive long todoId) {
        todoService.deleteTodo(todoId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity deleteTodos() {
        todoService.deleteTodos();

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
