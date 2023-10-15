package com.example.todo.auth;

import com.example.todo.request.AuthRequest;
import com.example.todo.request.RegisterRequest;
import com.example.todo.response.AuthResponse;
import com.example.todo.response.EmailAlreadyExistsException;
import com.example.todo.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService service;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) throws EmailAlreadyExistsException {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/auth")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest request){
        return ResponseEntity.ok(service.authenticate(request));
    }
}
