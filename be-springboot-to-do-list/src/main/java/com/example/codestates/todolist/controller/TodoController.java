package com.example.codestates.todolist.controller;

import com.example.codestates.todolist.dto.TodoPatchDto;
import com.example.codestates.todolist.dto.TodoPostDto;
import com.example.codestates.todolist.entity.Todo;
import com.example.codestates.todolist.mapper.TodoMapper;
import com.example.codestates.todolist.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoController {
    private final TodoService todoService;

    private final TodoMapper todoMapper;

    public TodoController(TodoService todoService, TodoMapper todoMapper) {
        this.todoService = todoService;
        this.todoMapper = todoMapper;
    }

    @PostMapping
    public ResponseEntity createTodo(@Valid @RequestBody TodoPostDto todoPostDto) {
        Todo todo = todoMapper.todoPostDtoToTodo(todoPostDto);
        Todo savedTodo = todoService.createTodo(todo);
        return new ResponseEntity<>(todoMapper.todoToTodoResponseDto(savedTodo),
                HttpStatus.CREATED);
    }

    @GetMapping("/{todo-id}")
    public ResponseEntity findTodo(@PathVariable("todo-id") @Positive long todoId){
        Todo todo = todoService.findTodo(todoId);
        return new ResponseEntity<>(todo, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity findTodos(){
        List<Todo> todos = todoService.findTodos();
        return new ResponseEntity<>(todos, HttpStatus.OK);
    }

    @PatchMapping("/{todo-id}")
    public ResponseEntity patchTodo(@PathVariable("todo-id") @Positive long todoId,
                                    @Valid @RequestBody TodoPatchDto todoPatchDto){
        Todo todo = todoMapper.todoPatchDtoToTodo(todoPatchDto);
        Todo patchTodo = todoService.patchTodo(todo);
        return new ResponseEntity<>(todoMapper.todoToTodoResponseDto(patchTodo), HttpStatus.OK);
    }

    @DeleteMapping("/{todo-id}")
    public ResponseEntity deleteTodo(@PathVariable("todo-id") @Positive long todoId) {
        todoService.deleteTodo(todoId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity deleteTodos(){
        todoService.deleteTodos();

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
