package com.example.cafe.controller;

import com.example.cafe.security.CustomUserDetailsService;
import com.example.cafe.security.LoginRequest;
import com.example.cafe.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sign-in")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;

    public AuthController(AuthenticationManager authenticationManager, CustomUserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping
    public ResponseEntity<?> authenticate(@RequestBody LoginRequest loginRequest) {
        try {
            // Проверка учетных данных
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );

            // Сохраняем аутентификацию в SecurityContext
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Извлекаем пользователя из контекста безопасности
            User authenticatedUser = (User) authentication.getPrincipal();

            // Возвращаем данные пользователя
            return ResponseEntity.ok(authenticatedUser); // Возвращаем объект User с данными о пользователе
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
}
