package com.example.todo.service;

import com.example.todo.repository.TodoRepository;
import com.example.todo.todo.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class TodoService {
    @Autowired
    TodoRepository todoRepository;
    Todo todo;

    public Todo addTodo(Todo todo,@AuthenticationPrincipal UserDetails userDetails){
        todo.setEmail(userDetails.getUsername());
        return todoRepository.save(todo);
    }
}
