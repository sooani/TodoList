package com.example.codestates.todolist.service;

import com.example.codestates.todolist.entity.Todo;
import com.example.codestates.todolist.repository.TodoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TodoService {
    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public Todo createTodo(Todo todo) {
        Todo savedTodo = todoRepository.save(todo);

        return savedTodo;
    }

    public Todo findTodo(long todoId) {
        return findVerifiedTodo(todoId);
    }

    public List<Todo> findTodos() {
        return todoRepository.findAll();
    }

    public Todo patchTodo(Todo todo) {
        Todo findTodo = findVerifiedTodo(todo.getTodoId());

        Optional.ofNullable(todo.getTitle())
                .ifPresent(title -> findTodo.setTitle(title));
        Optional.ofNullable(todo.getTodoOrder())
                .ifPresent(todoOrder -> findTodo.setTodoOrder(todoOrder));
        Optional.ofNullable(todo.isCompleted())
                .ifPresent(completed -> findTodo.setCompleted(completed));

        return todoRepository.save(findTodo);
    }

    public void deleteTodo(long todoId) {
        Todo deleteTodo = findVerifiedTodo(todoId);

        todoRepository.delete(deleteTodo);
    }

    public void deleteTodos() {
        todoRepository.deleteAll();
    }

    private Todo findVerifiedTodo(long todoId) {
        Optional<Todo> optionalTodo =
                todoRepository.findById(todoId);
        return optionalTodo.orElseThrow(() ->
                new RuntimeException("Todo not found"));
    }

}
