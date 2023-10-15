package com.example.todo.service;

import com.example.todo.config.JwtService;
import com.example.todo.repository.UserRepository;
import com.example.todo.request.AuthRequest;
import com.example.todo.request.RegisterRequest;
import com.example.todo.response.AuthResponse;
import com.example.todo.response.EmailAlreadyExistsException;
import com.example.todo.user.Role;
import com.example.todo.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) throws EmailAlreadyExistsException {

        if (userRepository.existsByEmail(request.getEmail())) {
            // You can handle this case, e.g., by throwing an exception or returning an error response
            throw new EmailAlreadyExistsException("Email already exists");
        }
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(()->
                        new RuntimeException("User not found with email: " + request.getEmail()));
        var jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }
}
