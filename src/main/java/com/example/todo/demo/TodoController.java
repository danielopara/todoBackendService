package com.example.todo.demo;

import com.example.todo.service.TodoService;
import com.example.todo.todo.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/todo")
public class TodoController {
    @Autowired
    TodoService todoService;
    @GetMapping
    public ResponseEntity<String> hello(@AuthenticationPrincipal UserDetails userDetails){
        return ResponseEntity.ok("hello" + userDetails.getUsername());
    }

    @PostMapping("addTodo")
    public ResponseEntity<Todo> addTodo(@RequestBody Todo todo, @AuthenticationPrincipal UserDetails userDetails){

        return new ResponseEntity<Todo>(todoService.addTodo(todo, userDetails), HttpStatus.OK);
    }

    @GetMapping("todo")
    public ResponseEntity<List<Todo>> getTodo(@AuthenticationPrincipal UserDetails userDetails){
        String userEmail = userDetails.getUsername();
        List<Todo> todos = todoService.allTodosByEmail(userEmail);
        return ResponseEntity.ok(todos);
    }
}
