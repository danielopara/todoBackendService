package com.example.todo.demo;

import com.example.todo.service.TodoService;
import com.example.todo.todo.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/demo")
public class DemoController {
    @Autowired
    TodoService todoService;
    @GetMapping
    public ResponseEntity<String> hello(@AuthenticationPrincipal UserDetails userDetails){
        return ResponseEntity.ok("hello" + userDetails.getUsername());
    }

    @PostMapping("addTodo")
    public ResponseEntity<Todo> addPlayer(@RequestBody Todo todo, @AuthenticationPrincipal UserDetails userDetails){

        return new ResponseEntity<Todo>(todoService.addTodo(todo, userDetails), HttpStatus.OK);
    }
}
