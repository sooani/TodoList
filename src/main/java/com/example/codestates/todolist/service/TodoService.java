package com.example.codestates.todolist.service;

import com.example.codestates.todolist.entity.Todo;
import com.example.codestates.todolist.exception.BusinessLogicException;
import com.example.codestates.todolist.exception.ExceptionCode;
import com.example.codestates.todolist.repository.TodoRepository;
import com.example.codestates.todolist.utils.CustomBeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional //
public class TodoService {
    private final TodoRepository todoRepository;
    private final CustomBeanUtils<Todo> beanUtils;

    public TodoService(TodoRepository todoRepository, CustomBeanUtils<Todo> beanUtils) {
        this.todoRepository = todoRepository;
        this.beanUtils = beanUtils;
    }

    // Todo 생성
    public Todo createTodo(Todo todo) {
        Todo savedTodo = todoRepository.save(todo);

        return savedTodo;
    }

    @Transactional(readOnly = true) //읽기전용
    public Todo findTodo(long todoId) {
        return findVerifiedTodo(todoId);
    }

    public Page<Todo> findTodos(int page, int size) { // 페이지는 0부터 시작 1을 눌렀을때 0을 가져옴
        return todoRepository.findAll(PageRequest.
                of(page , size, Sort.by("todoId").descending()));
    }

    public Todo patchTodo(Todo todo) {
        Todo findTodo = findVerifiedTodo(todo.getTodoId());

        Todo updatingTodo = beanUtils.copyNonNullProperties(todo, findTodo);

        return todoRepository.save(updatingTodo);
    }

    public void deleteTodo(long todoId) {
        Todo deleteTodo = findVerifiedTodo(todoId);

        todoRepository.delete(deleteTodo);
    }

    public void deleteTodos() {
        todoRepository.deleteAll();
    }

    private Todo findVerifiedTodo(long todoId) {
        Optional<Todo> optionalTodo = //반환 옵셔널일때 예외처리 해줘야함
                todoRepository.findById(todoId);
        Todo findTodo =
                optionalTodo.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.TODO_NOT_FOUND));
        return findTodo;
    }
}
