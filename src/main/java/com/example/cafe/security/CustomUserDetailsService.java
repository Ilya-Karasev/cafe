package com.example.cafe.security;

import com.example.cafe.model.User;
import com.example.cafe.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Поиск пользователя: " + username); // Логируем запрос
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        System.out.println("Найден пользователь: " + user.getUsername()); // Логируем найденного пользователя

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
               // new ArrayList<>(),// Используем зашифрованный пароль
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }
}

