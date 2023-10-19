package com.example.todo.demo;

import com.example.todo.service.TodoService;
import com.example.todo.todo.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<Optional<Todo>> getTodo(@AuthenticationPrincipal UserDetails userDetails){
        String userEmail = userDetails.getUsername();
        Optional<Todo> todos = todoService.allTodosByEmail(userEmail);
        return ResponseEntity.ok(todos);
    }

    @PutMapping("/updateTodo/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable Long id ,@AuthenticationPrincipal UserDetails userDetails ,@RequestBody Todo todo){
        return new ResponseEntity<Todo>(todoService.updateTodo(id, userDetails, todo), HttpStatus.OK);
    }
}
