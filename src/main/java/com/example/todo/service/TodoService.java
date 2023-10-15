package com.example.todo.service;

import com.example.todo.repository.TodoRepository;
import com.example.todo.todo.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<Todo> allTodosByEmail( String userEmail){
        return todoRepository.findByEmail(userEmail);
    }
}
