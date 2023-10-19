package com.example.todo.service;

import com.example.todo.exception.UnauthorizedAccessException;
import com.example.todo.repository.TodoRepository;
import com.example.todo.todo.Todo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {
    @Autowired
    TodoRepository todoRepository;

    public Todo addTodo(Todo todo,@AuthenticationPrincipal UserDetails userDetails){
        Long maxTaskId = todoRepository.findMaxTaskId(userDetails.getUsername());
        Long newTaskId = (maxTaskId != null) ? maxTaskId + 1 : 1;
        todo.setEmail(userDetails.getUsername());
        todo.setTaskId(newTaskId);
        return todoRepository.save(todo);
    }

    public Optional<Todo> allTodosByEmail( String userEmail){
        return todoRepository.findByEmail(userEmail);
    }

    public Todo updateTodo(Long id, @AuthenticationPrincipal UserDetails userDetails, Todo todo){
        Todo updateTodo = todoRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("todo does not exist"));

        if(!updateTodo.getEmail().equals(userDetails.getUsername())){
            throw new UnauthorizedAccessException("You don't have permission to change this task");
        }
        updateTodo.setActive(todo.isActive());
        updateTodo.setTask(todo.getTask());
        return todoRepository.save(updateTodo);
    }
}
