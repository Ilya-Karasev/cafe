//package com.example.cafe.controller;
//
////import com.example.cafe.security.LoginRequest;
//import com.example.cafe.model.User;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/sign-in")
//public class AuthController {
//
//    private final AuthenticationManager authenticationManager;
//    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
//
//    public AuthController(AuthenticationManager authenticationManager) {
//        this.authenticationManager = authenticationManager;
//    }
//
//    @PostMapping
//    public ResponseEntity<?> authenticate(@RequestBody LoginRequest loginRequest) {
//        try {
//            // Проверка учетных данных
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
//            );
//
//            // Сохраняем аутентификацию в SecurityContext
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//
//            // Извлекаем пользователя из контекста безопасности
//            User authenticatedUser = (User) authentication.getPrincipal();
//
//            // Логируем информацию о пользователе
//            logger.info("User authenticated successfully: username={}, email={}, phone={}",
//                    authenticatedUser.getUsername(),
//                    authenticatedUser.getEmail(),
//                    authenticatedUser.getPhone());
//            System.out.println("Найден пользователь: " + authenticatedUser.getUsername() + ", " + authenticatedUser.getPassword() + ", " + authenticatedUser.getEmail() + ", " + authenticatedUser.isAdmin() + ", " + authenticatedUser.getPhone()); // Логируем найденного пользователя
//
//            // Возвращаем данные пользователя
//            return ResponseEntity.ok(authenticatedUser); // Возвращаем объект User с данными о пользователе
//        } catch (BadCredentialsException e) {
//            logger.error("Authentication failed for username: {}", loginRequest.getUsername());
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
//        }
//    }
//}